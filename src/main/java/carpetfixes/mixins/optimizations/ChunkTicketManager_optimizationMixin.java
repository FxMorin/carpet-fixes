package carpetfixes.mixins.optimizations;

import carpetfixes.CFSettings;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.collection.SortedArraySet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Predicate;

@Mixin(ChunkTicketManager.class)
public abstract class ChunkTicketManager_optimizationMixin {

    @Shadow
    private long age;

    @Shadow
    @Final
    Long2ObjectOpenHashMap<SortedArraySet<ChunkTicket<?>>> ticketsByPosition;

    @Shadow
    @Final
    private ChunkTicketManager.TicketDistanceLevelPropagator distanceFromTicketTracker;

    @Shadow
    private static int getLevel(SortedArraySet<ChunkTicket<?>> sortedArraySet) {
        throw new UnsupportedOperationException();
    }

    private final Long2ObjectOpenHashMap<SortedArraySet<ChunkTicket<?>>> positionWithExpiringTicket =
            new Long2ObjectOpenHashMap<>();

    private static boolean canAnyExpire(SortedArraySet<ChunkTicket<?>> tickets) {
        for (ChunkTicket<?> ticket : tickets) {
            if (canExpire(ticket)) return true;
        }
        return false;
    }

    private static boolean canExpire(ChunkTicket<?> ticket) {
        return ticket.getType().getExpiryTicks() != 0;
    }

    @Shadow
    public abstract boolean tick(ThreadedAnvilChunkStorage threadedAnvilChunkStorage);

    /**
     * @reason Remove lambda allocation in every iteration
     * @author JellySquid
     * Mark all locations that have tickets that can expire as such. Allows iterating only over locations with
     * tickets that can expire when purging expired tickets.
     */
    @SuppressWarnings("all")
    @Inject(
            method = "addTicket(JLnet/minecraft/server/world/ChunkTicket;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/collection/SortedArraySet;" +
                            "addAndGet(Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void registerExpiringTicket(long position, ChunkTicket<?> ticket, CallbackInfo ci,
                                        SortedArraySet<ChunkTicket<?>> ticketsAtPos, int i) {
        if (CFSettings.optimizedTicketManager && canExpire(ticket)) {
            this.positionWithExpiringTicket.put(position, ticketsAtPos);
        }
    }

    @Inject(
            method = "removeTicket(JLnet/minecraft/server/world/ChunkTicket;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ChunkTicketManager$TicketDistanceLevelPropagator;" +
                            "updateLevel(JIZ)V"
            )
    )
    private void unregisterExpiringTicket(long pos, ChunkTicket<?> ticket, CallbackInfo ci) {
        if (CFSettings.optimizedTicketManager && canExpire(ticket)) {
            SortedArraySet<ChunkTicket<?>> ticketsAtPos = this.positionWithExpiringTicket.get(pos);
            if (!canAnyExpire(ticketsAtPos)) this.positionWithExpiringTicket.remove(pos);
        }
    }

    /**
     * @reason Remove lambda allocation in every iteration, only iterate over ticks that can expire
     * @author JellySquid, 2No2Name
     */
    @Inject(
            method = "purge()V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void purge(CallbackInfo ci) {
        if (CFSettings.optimizedTicketManager) {
            ++this.age;

            ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<ChunkTicket<?>>>> iterator =
                    this.ticketsByPosition.long2ObjectEntrySet().fastIterator();
            Predicate<ChunkTicket<?>> predicate = (chunkTicket) -> chunkTicket.isExpired(this.age);
            this.positionWithExpiringTicket.long2ObjectEntrySet().fastIterator();
            Predicate<ChunkTicket<?>> isExpiredPredicate = (chunkTicket) -> chunkTicket.isExpired(this.age);

            while (iterator.hasNext()) {
                Long2ObjectMap.Entry<SortedArraySet<ChunkTicket<?>>> entry = iterator.next();
                SortedArraySet<ChunkTicket<?>> value = entry.getValue();
                SortedArraySet<ChunkTicket<?>> ticketsAtPos = entry.getValue();
                long pos = entry.getLongKey();

                if (value.removeIf(predicate)) {
                    this.distanceFromTicketTracker.updateLevel(entry.getLongKey(), getLevel(entry.getValue()), false);
                }
                if (ticketsAtPos.removeIf(isExpiredPredicate)) {
                    assert this.ticketsByPosition.get(pos) == ticketsAtPos;
                    this.distanceFromTicketTracker.updateLevel(pos, getLevel(ticketsAtPos), false);

                    if (value.isEmpty()) {
                        iterator.remove();
                        if (!canAnyExpire(ticketsAtPos)) iterator.remove();
                        if (ticketsAtPos.isEmpty()) this.ticketsByPosition.remove(pos, ticketsAtPos);
                    }
                }
            }
            ci.cancel();
        }
    }
}

package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes bee duplication by preventing bees from interacting with unloaded chunks
 */
@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveBlockEntity_dupeMixin extends BlockEntity {

    public BeehiveBlockEntity_dupeMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Inject(
            method = "tryEnterHive(Lnet/minecraft/entity/Entity;ZI)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$tryEnterHiveIfLoaded(Entity entity, boolean hasNectar, int ticksInHive, CallbackInfo ci) {
        if (CFSettings.beeDupeFix && !entity.getWorld().isChunkLoaded(
                ChunkSectionPos.getSectionCoord(this.pos.getX()),
                ChunkSectionPos.getSectionCoord(this.pos.getY())
        )) {
            ci.cancel();
        }
    }
}

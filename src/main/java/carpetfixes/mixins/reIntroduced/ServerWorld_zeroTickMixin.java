package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_zeroTickMixin extends World {

    protected ServerWorld_zeroTickMixin(MutableWorldProperties properties, RegistryKey<World> registryRef,
                                        DynamicRegistryManager registryManager,
                                        RegistryEntry<DimensionType> dimensionEntry, Supplier<Profiler> profiler,
                                        boolean isClient, boolean debugWorld, long biomeAccess,
                                        int maxChainedNeighborUpdates) {
        super(properties, registryRef, registryManager, dimensionEntry, profiler, isClient,
                debugWorld, biomeAccess, maxChainedNeighborUpdates);
    }


    @Inject(
            method = "tickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;scheduledTick(" +
                            "Lnet/minecraft/server/world/ServerWorld;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void cf$zeroTickBlock(BlockPos pos, Block block, CallbackInfo ci, BlockState state) {
        if (CFSettings.reIntroduceZeroTickFarms && !this.getBlockState(pos).isOf(block) && state.hasRandomTicks()) {
            state.randomTick((ServerWorld) (Object) this, pos, this.random);
        }
    }
}

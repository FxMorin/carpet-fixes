package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
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

    private final ServerWorld self = (ServerWorld)(Object)this;

    protected ServerWorld_zeroTickMixin(MutableWorldProperties properties, RegistryKey<World> registryRef,
                                        RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler,
                                        boolean isClient, boolean debugWorld, long seed, int i) {
        super(properties, registryRef, registryEntry, profiler, isClient, debugWorld, seed, i);
    }


    @Inject(
            method = "tickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;scheduledTick(" +
                            "Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/world/gen/random/AbstractRandom;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void zeroTickBlock(BlockPos pos, Block block, CallbackInfo ci, BlockState state) {
        if (CFSettings.reIntroduceZeroTickFarms && !this.isAir(pos) && state.hasRandomTicks())
            state.randomTick(self,pos,this.random);
    }
}

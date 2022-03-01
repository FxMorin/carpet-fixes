package carpetfixes.mixins.oldSupport;

import carpetfixes.CFSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;
import java.util.function.Supplier;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.LT_22w06a))
@Mixin(World.class)
class old_World_randomMixin {

    @Mutable
    @Shadow
    @Final
    public Random random;


    @SuppressWarnings("all")
    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void CustomRandom(MutableWorldProperties properties, RegistryKey registryRef, DimensionType dimensionType,
                              Supplier profiler, boolean isClient, boolean debugWorld, long seed, CallbackInfo ci) {
        if (CFSettings.optimizedRandom) this.random = new XoroshiroCustomRandom();
    }
}

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.LT_22w06a))
@Mixin(ServerWorld.class)
class ServerWorld_zeroTickMixin {

    private final ServerWorld self = (ServerWorld)(Object)this;


    @SuppressWarnings("all")
    @Inject(
            method = "tickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;scheduledTick(Lnet/minecraft/server/world/ServerWorld;" +
                            "Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void zeroTickBlock(BlockPos pos, Block block, CallbackInfo ci, BlockState state) {
        if (CFSettings.reIntroduceZeroTickFarms && !self.isAir(pos) && state.hasRandomTicks()) {
            state.randomTick(self,pos,self.random);
        }
    }
}

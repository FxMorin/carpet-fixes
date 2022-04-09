package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LavaFluid.class)
public class LavaFluid_wrongStateMixin {


    @ModifyArg(
            method = "onRandomTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/fluid/FluidState;Lnet/minecraft/world/gen/random/AbstractRandom;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractFireBlock;getState(Lnet/minecraft/world/BlockView;" +
                            "Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 1
            ),
            index = 1
    )
    private BlockPos modifyBlockPosHeight(BlockPos pos) {
        return CFSettings.lavaCalculatesWrongFireStateFix ? pos.up() : pos;
    }
}

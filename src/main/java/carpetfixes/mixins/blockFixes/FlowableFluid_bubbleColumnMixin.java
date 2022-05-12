package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FlowableFluid.class)
public class FlowableFluid_bubbleColumnMixin {


    @Redirect(
            method = "canFill",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
                    ordinal = 2
            ),
            require = 0
    )
    private boolean canFill(BlockState instance, Block block) {
        return !CFSettings.lavaIgnoresBubbleColumnFix && instance.isOf(block);
    }
}

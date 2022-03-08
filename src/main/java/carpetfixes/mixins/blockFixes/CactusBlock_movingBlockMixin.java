package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(CactusBlock.class)
public class CactusBlock_movingBlockMixin {


    @Redirect(
            method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;" +
                    "Lnet/minecraft/util/math/BlockPos;)Z",
            slice = @Slice(
                    from = @At("HEAD"),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getMaterial()Lnet/minecraft/block/Material;"
            )
    )
    private Material changeMaterialIfMovingPiston(BlockState state) {
        return CFSettings.nonSolidBlocksBreakCactusIfPushedFix && state.isOf(Blocks.MOVING_PISTON) ?
                Material.AIR : state.getMaterial();
    }
}

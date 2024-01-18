package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes powder snow not affecting you when your feet are not in powdered snow, and your center is not in powered snow
 */

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlock_feetAndCenterMixin {


    @Redirect(
            method = "onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getBlockStateAtPos()Lnet/minecraft/block/BlockState;"
            ),
            require = 0
    )
    private BlockState powderSnowNotJustFeet(Entity entity) {
        if (CFSettings.powderSnowOnlySlowIfFeetInBlockFix && entity instanceof PlayerEntity) {
            BlockState stateAbove = entity.getWorld().getBlockState(entity.getBlockPos().up());
            if (stateAbove.isOf(Blocks.POWDER_SNOW)) return stateAbove;
        }
        return entity.getBlockStateAtPos();
    }
}

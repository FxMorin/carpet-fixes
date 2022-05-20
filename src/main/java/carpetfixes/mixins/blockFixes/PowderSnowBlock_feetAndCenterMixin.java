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

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlock_feetAndCenterMixin {


    @Redirect(
            method = "onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getBlockStateAtPos()Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState powderSnowNotJustFeet(Entity entity) {
        if (CFSettings.entityBlockCollisionUsingCenterFix && entity instanceof PlayerEntity)
            return Blocks.POWDER_SNOW.getDefaultState();
        if (CFSettings.powderSnowOnlySlowIfFeetInBlockFix && entity instanceof PlayerEntity) {
            BlockState stateAbove = entity.world.getBlockState(entity.getBlockPos().up());
            if (stateAbove.isOf(Blocks.POWDER_SNOW)) return stateAbove;
        }
        return entity.getBlockStateAtPos();
    }
}

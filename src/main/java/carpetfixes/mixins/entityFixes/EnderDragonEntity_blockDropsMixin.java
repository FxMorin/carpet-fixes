package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntity_blockDropsMixin {


    @Redirect(
            method = "destroyBlocks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
            )
    )
    private boolean letsDropTheBlocks(World world, BlockPos pos, boolean move) {
        if (CFSettings.enderDragonDoesntDropBlocksFix) {
            BlockState blockState = world.getBlockState(pos);
            BlockEntity blockEntity = blockState.hasBlockEntity() ? world.getBlockEntity(pos) : null;
            Block.dropStacks(blockState, world, pos, blockEntity, null, ItemStack.EMPTY);
        }
        return world.removeBlock(pos, move);
    }
}

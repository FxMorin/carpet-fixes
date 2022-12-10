package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fixes records not creating the record item in the correct location, causing it to clip in the block above
 */

@Mixin(JukeboxBlock.class)
public class JukeboxBlock_itemOffsetMixin {

    private static boolean shouldDropCentered = false;


    @Inject(
            method = "onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/JukeboxBlock;removeRecord(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void onStateReplacedDropCentered(BlockState state, World world, BlockPos pos,
                                            BlockState newState, boolean moved, CallbackInfo ci) {
        if (CFSettings.jukeboxDiscItemOffsetOnBreakFix) shouldDropCentered = true;
    }


    @Inject(
            method = "removeRecord(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;clear()V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void modifyHeight(World world, BlockPos pos, CallbackInfo ci, BlockEntity blockEntity,
                              JukeboxBlockEntity jukeboxBlockEntity, ItemStack itemStack) {
        if (shouldDropCentered) { // Use modern item scatterer code
            double width = EntityType.ITEM.getWidth();
            double timesAmt = 1.0 - width;
            double horizontalOffset = width / 2.0;
            double x = world.random.nextDouble() * timesAmt + horizontalOffset;
            double y = world.random.nextDouble() * timesAmt;
            double z = world.random.nextDouble() * timesAmt + horizontalOffset;
            ItemStack itemStack2 = itemStack.copy();
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + x, pos.getY() + y, pos.getZ() + z, itemStack2);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
            ci.cancel();
        }
    }
}

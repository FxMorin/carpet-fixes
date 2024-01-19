package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RedstoneView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents Headless pistons from existing, since headless pistons are able to break any block in the game.
 * This fix should prevent being able to break most blocks such as bedrock!
 */

@Mixin(PistonBlock.class)
public abstract class PistonBlock_headlessMixin extends FacingBlock {


    @Shadow
    @Final
    public static BooleanProperty EXTENDED;

    @Shadow
    @Final
    private boolean sticky;

    protected PistonBlock_headlessMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    @Final
    private boolean shouldExtend(RedstoneView world, BlockPos pos, Direction pistonFace) {
        return true;
    }


    @Inject(
            method = "tryMove(Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/block/PistonBlock;shouldExtend(Lnet/minecraft/world/RedstoneView;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void cf$stopHeadlessPiston(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CFSettings.headlessPistonFix && state.get(EXTENDED)) {
            Direction direction = state.get(FACING);
            BlockState blockState = world.getBlockState(pos.offset(direction));
            if (this.shouldExtend(world, pos, direction) &&
                    !blockState.isOf(Blocks.MOVING_PISTON) &&
                    !blockState.isOf(Blocks.PISTON_HEAD)) {
                world.removeBlock(pos, false);
                ItemEntity itemEntity = new ItemEntity(
                        world,
                        pos.getX(), pos.getY(), pos.getZ(),
                        new ItemStack(this.sticky ? Items.STICKY_PISTON : Items.PISTON)
                );
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
                ci.cancel();
            }
        }
    }
}

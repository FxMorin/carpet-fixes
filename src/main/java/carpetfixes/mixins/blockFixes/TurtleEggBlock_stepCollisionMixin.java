package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.CollisionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TurtleEggBlock.class)
public abstract class TurtleEggBlock_stepCollisionMixin extends Block {

    @Shadow
    public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

    public TurtleEggBlock_stepCollisionMixin(Settings settings) {
        super(settings);
    }


    @Inject(
            method = "onSteppedOn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onStepOnCheckCollision(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (CFSettings.turtleEggWrongCollisionCheckFix &&
                !CollisionUtils.isEntityTouchingState(world, pos, entity, state)) {
            super.onSteppedOn(world, pos, state, entity);
            ci.cancel();
        }
    }


    @Inject(
            method = "onLandedUpon",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onLandedUponCheckCollision(World world, BlockState state, BlockPos pos,
                              Entity entity, float fallDistance, CallbackInfo ci) {
        if (CFSettings.turtleEggWrongCollisionCheckFix &&
                !CollisionUtils.isEntityTouchingState(world, pos, entity, state)) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
            ci.cancel();
        }
    }
}

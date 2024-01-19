package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.CollisionUtils;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes bed blocks being collided against without actually touching the bed
 */
@Mixin(BedBlock.class)
public class BedBlock_collisionMixin extends Block {

    public BedBlock_collisionMixin(Settings settings) {
        super(settings);
    }


    @Inject(
            method = "onLandedUpon",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$onLandedCheckCollision(World world, BlockState state, BlockPos pos,
                                        Entity entity, float fallDistance, CallbackInfo ci) {
        if (CFSettings.bedLandingWrongCollisionFix &&
                !CollisionUtils.isEntityTouchingState(world, pos, entity, state)) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
            ci.cancel();
        }
    }
}

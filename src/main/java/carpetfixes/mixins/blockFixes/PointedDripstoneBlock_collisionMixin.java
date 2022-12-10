package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.CollisionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes taking pointed dripstone damage even if you didn't touch dripstone
 */

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlock_collisionMixin extends Block {

    public PointedDripstoneBlock_collisionMixin(Settings settings) {
        super(settings);
    }


    @Inject(
            method = "onLandedUpon",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;" +
                            "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"
            ),
            cancellable = true
    )
    private void onLandedUponCheckCollision(World world, BlockState state, BlockPos pos,
                              Entity entity, float fallDistance, CallbackInfo ci) {
        if (CFSettings.pointedDripstoneWrongCollisionFix &&
                !CollisionUtils.isEntityTouchingState(world, pos, entity, state)) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
            ci.cancel();
        }
    }
}

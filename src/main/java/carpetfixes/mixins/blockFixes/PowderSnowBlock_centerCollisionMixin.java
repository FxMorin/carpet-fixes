package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlock_centerCollisionMixin {

    /**
     * Makes it so that powdered snow starts slowing you down once you touch it, instead of once
     * the center of your player touches it.
     */


    @Redirect(
            method = "onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;" +
                            "slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"
            )
    )
    public void slowMovementAtNewLocation(Entity entity, BlockState state, Vec3d multiplier) {
        if (!CFSettings.playerBlockCollisionUsingCenterFix) entity.slowMovement(state,multiplier);
    }


    @Inject(
            method = "onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V",
            at = @At("HEAD")
    )
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (CFSettings.playerBlockCollisionUsingCenterFix)
            entity.slowMovement(state,new Vec3d(0.9F, 1.5, 0.9F));
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.CenterUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class Entity_centerCollisionMixin implements EntityLike {

    /**
     * Player effects for blocks that you stand on are chosen based on which block is directly below the center of the
     * player. Instead of checking all the blocks you are standing on. What I am doing here is checking each block you
     * are standing on. I am also doing hitboxes, not just blocks the player is standing on.
     */


    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    public World world;


    @Redirect(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onSteppedOn(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;" +
                            "Lnet/minecraft/entity/Entity;)V"
            )
    )
    public void onSteppingCollisionCheck(Block block, World world, BlockPos pos, BlockState state, Entity entity) {
        if (CFSettings.playerBlockCollisionUsingCenterFix) {
            CenterUtils.checkStepOnCollision(entity);
        } else {
            block.onSteppedOn(world, pos, state, entity);
        }
    }


    @Redirect(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;" +
                            "onEntityLand(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;)V"
            )
    )
    public void onEntityLandCollisionCheck(Block block, BlockView world, Entity entity) {
        if (CFSettings.playerBlockCollisionUsingCenterFix) {
            CenterUtils.checkEntityLandOnCollision(entity);
        } else {
            block.onEntityLand(world, entity);
        }
    }


    @Redirect(
            method = "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onLandedUpon(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/entity/Entity;F)V"
            )
    )
    public void onFallCollisionCheck(Block block, World world, BlockState state,
                                     BlockPos pos, Entity entity, float fallDistance) {
        if (CFSettings.playerBlockCollisionUsingCenterFix) {
            CenterUtils.checkFallCollision(entity,fallDistance);
        } else {
            block.onLandedUpon(world, state, pos, entity, fallDistance);
        }
    }


    @Redirect(
            method = "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/tag/TagKey;)Z"
            )
    )
    public boolean onFallStopVibration(BlockState blockState, TagKey<Block> tagKey) {
        return CFSettings.playerBlockCollisionUsingCenterFix || blockState.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
    }


    @Inject(
            method = "getJumpVelocityMultiplier()F",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onJumpVelocityCollisionCheck(CallbackInfoReturnable<Float> cir) {
        if (CFSettings.playerBlockCollisionUsingCenterFix)
            cir.setReturnValue(CenterUtils.checkJumpVelocityOnCollision(this.getBoundingBox(),this.world));
    }


    @Inject(
            method = "getVelocityMultiplier()F",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onVelocityCollisionCheck(CallbackInfoReturnable<Float> cir) {
        if (CFSettings.playerBlockCollisionUsingCenterFix)
            cir.setReturnValue(CenterUtils.checkVelocityOnCollision(this.getBoundingBox(),this.world));
    }
}

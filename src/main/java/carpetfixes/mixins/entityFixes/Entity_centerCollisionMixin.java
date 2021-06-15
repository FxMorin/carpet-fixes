package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesInit;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
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

    @Shadow public abstract Box getBoundingBox();
    @Shadow public World world;

    @Redirect(method= "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",at=@At(value="INVOKE",target="Lnet/minecraft/block/Block;onSteppedOn(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/Entity;)V"))
    public void onSteppingCollisionCheck(Block block, World world, BlockPos pos, BlockState state, Entity entity) {
        if (CarpetFixesSettings.playerBlockCollisionUsingCenterFix) {
            CarpetFixesInit.checkStepOnCollision(entity);
        } else {
            block.onSteppedOn(world, pos, state, entity);
        }
    }

    @Redirect(method= "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",at=@At(value="INVOKE",target="Lnet/minecraft/block/Block;onEntityLand(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;)V"))
    public void onEntityLandCollisionCheck(Block block, BlockView world, Entity entity) {
        if (CarpetFixesSettings.playerBlockCollisionUsingCenterFix) {
            CarpetFixesInit.checkEntityLandOnCollision(entity);
        } else {
            block.onEntityLand(world, entity);
        }
    }

    @Redirect(method= "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",at=@At(value="INVOKE",target="Lnet/minecraft/block/Block;onLandedUpon(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V"))
    public void onFallCollisionCheck(Block block, World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (CarpetFixesSettings.playerBlockCollisionUsingCenterFix) {
            CarpetFixesInit.checkFallCollision(entity,fallDistance);
        } else {
            block.onLandedUpon(world, state, pos, entity, fallDistance);
        }
    }

    @Redirect(method= "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",at=@At(value="INVOKE",target="Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/tag/Tag;)Z"))
    public boolean onFallStopVibration(BlockState blockState, Tag<Block> tag) {
        return CarpetFixesSettings.playerBlockCollisionUsingCenterFix || blockState.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
    }

    @Inject(method= "getJumpVelocityMultiplier()F",at=@At(value="HEAD"),cancellable = true)
    public void onJumpVelocityCollisionCheck(CallbackInfoReturnable<Float> cir) {
        if (CarpetFixesSettings.playerBlockCollisionUsingCenterFix) {
            cir.setReturnValue(CarpetFixesInit.checkJumpVelocityOnCollision(this.getBoundingBox(),this.world));
        }
    }

    @Inject(method= "getVelocityMultiplier()F",at=@At(value="HEAD"),cancellable = true)
    public void onVelocityCollisionCheck(CallbackInfoReturnable<Float> cir) {
        if (CarpetFixesSettings.playerBlockCollisionUsingCenterFix) {
            cir.setReturnValue(CarpetFixesInit.checkVelocityOnCollision(this.getBoundingBox(),this.world));
        }
    }
}

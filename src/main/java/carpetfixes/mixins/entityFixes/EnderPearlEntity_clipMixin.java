package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.ExtendedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntity_clipMixin extends ThrownItemEntity {

    public EnderPearlEntity_clipMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "onCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/thrown/EnderPearlEntity;isRemoved()Z",
                    shift = At.Shift.AFTER
            )
    )
    private void movePearlToValidCollisionSpot(HitResult hitResult, CallbackInfo ci) {
        if (CFSettings.enderpearlClipFix && hitResult instanceof BlockHitResult blockHitResult) {
            Entity owner = this.getOwner();
            if (owner == null) return;
            EntityDimensions dimensions = owner.getDimensions(owner.getPose());
            if (blockHitResult.getSide() == Direction.DOWN)
                this.setPos(this.getX(), (this.getY() - dimensions.height / 2) + 0.5, this.getZ());
            Vec3d blockBoundPos = new Vec3d(
                    Math.floor(this.getX()) + 0.5,
                    Math.floor(this.getY()) + 0.5,
                    Math.floor(this.getZ()) + 0.5
            );
            this.setPosition(blockBoundPos.add(((ExtendedEntity) owner).adjustMovementForCollisionsAtPos(
                    dimensions,
                    this.getPos().subtract(blockBoundPos),
                    blockBoundPos,
                    false
            )));
        }
    }
}

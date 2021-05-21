package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class Entity_blockCollisionMixin {

    @Shadow protected void checkBlockCollision() {}

    @Redirect(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;checkBlockCollision()V")
    )
    protected void onEntityCollision(Entity entity) {
        if (!CarpetFixesSettings.blockCollisionCheckFix) {
            this.checkBlockCollision();
        }
    }

    @Inject(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/Entity;adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;")
    )
    protected void InjectOnEntityCollisionHere(MovementType type, Vec3d movement, CallbackInfo ci) {
        if (CarpetFixesSettings.blockCollisionCheckFix) {
            this.checkBlockCollision();
        }
    }
}
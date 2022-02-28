package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntity_piercingMixin {

    private final ThreadLocal<Entity> lastEntity = new ThreadLocal<>();


    @Inject(
            method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;getPierceLevel()B",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    protected void onEndermanCheck(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity, float f, int i) {
        lastEntity.set(entity);
    }


    @Redirect(
            method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;getPierceLevel()B",
                    ordinal = 0
            )
    )
    protected byte skipForEnderman(PersistentProjectileEntity instance) {
        return CFSettings.endermanLowerPiercingFix ? lastEntity.get().getType() == EntityType.ENDERMAN ?
                0 :
                instance.getPierceLevel() : instance.getPierceLevel();
    }
}

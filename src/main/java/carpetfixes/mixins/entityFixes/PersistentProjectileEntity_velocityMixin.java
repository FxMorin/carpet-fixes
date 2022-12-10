package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes projectiles not losing there velocity when hitting a block
 */

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntity_velocityMixin extends Entity {

    public PersistentProjectileEntity_velocityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;age()V"
            )
    )
    private void removeVelocity(CallbackInfo ci) {
        if (CFSettings.projectileKeepsVelocityFix) this.setVelocity(Vec3d.ZERO);
    }
}

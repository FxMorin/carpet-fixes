package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes the endermen not being able to take damage from wither and tnt explosions
 * (<a href="https://bugs.mojang.com/browse/MC-258561">MC-258561</a>)
 */

@Mixin(EndermanEntity.class)
public abstract class EndermanEntity_explosionDamageMixin extends LivingEntity {

    protected EndermanEntity_explosionDamageMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @SuppressWarnings("UnusedReturnValue")
    @Invoker("teleportRandomly")
    public abstract boolean invokeTeleportRandomly();


    @Inject(
            method = "damage",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void cf$damageFromExplosion(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.endermanDontTakeExplosionDamageFix
                && !source.isIn(DamageTypeTags.IS_PROJECTILE)
                && !(source.getSource() instanceof PotionEntity)) {
            boolean bl = super.damage(source, amount);
            if (!this.getWorld().isClient() && !(source.getAttacker() instanceof LivingEntity) &&
                    this.random.nextInt(10) != 0) {
                this.invokeTeleportRandomly();
            }
            cir.setReturnValue(bl);
        }
    }
}

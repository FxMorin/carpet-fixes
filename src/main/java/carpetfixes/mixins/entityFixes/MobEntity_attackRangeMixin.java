package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntity_attackRangeMixin extends LivingEntity {

    protected MobEntity_attackRangeMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // This is a hackfix since doing a fix like this with mixins would require
    // around 30 mixins to be implemented correctly. Some entity animations may be broken

    @Inject(
            method = "tryAttack(Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void tryAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.mobsAttackThroughBlocksFix && !this.canSee(target)) cir.setReturnValue(false);
    }
}

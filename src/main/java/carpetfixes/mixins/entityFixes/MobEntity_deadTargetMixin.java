package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * As explained in my code analysis here: https://bugs.mojang.com/browse/MC-183990
 * Mobs that have the goal `RevengeGoal` with the option `setGroupRevenge()` will lead to mobs targeting
 * dead entities. We simply fix this by checking if the target is dead in the baseTick(), and if so we
 * set it to null.
 * This check is nearly identical to how other entity checks for tasks and mobs is handled
 */

@Mixin(MobEntity.class)
public abstract class MobEntity_deadTargetMixin extends LivingEntity {

    @Shadow
    private @Nullable LivingEntity target;

    protected MobEntity_deadTargetMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity target);


    @Inject(
            method = "baseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiler/Profiler;pop()V",
                    shift = At.Shift.BEFORE
            )
    )
    private void cf$removeDeadTarget(CallbackInfo ci) {
        if (CFSettings.mobsTargetDeadEntitiesFix && this.target != null && target.isDead()) {
            this.setTarget(null);
        }
    }
}

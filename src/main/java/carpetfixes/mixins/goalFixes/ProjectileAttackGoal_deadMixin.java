package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileAttackGoal.class)
public abstract class ProjectileAttackGoal_deadMixin {

    @Shadow
    @Final
    private MobEntity mob;

    @Shadow
    private @Nullable LivingEntity target;


    @Inject(
            method = "shouldContinue",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shouldContinueNotDead(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.projectileGoalTargetsDeadEntitiesFix)
            cir.setReturnValue(this.target != null && this.target.isAlive() && !this.mob.getNavigation().isIdle());
    }
}

package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Fixes the break door goal not stopping when the entity dies, causing the block breaking effect to stay for a while
 */

@Mixin(MobEntity.class)
public abstract class MobEntity_stopGoalOnDeathMixin extends LivingEntity {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    protected MobEntity_stopGoalOnDeathMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void updatePostDeath() {
        if (CFSettings.doorBreakNotStoppedOnDeathFix && this.deathTime == 0 && !this.getWorld().isClient())
            this.goalSelector.getRunningGoals().forEach((goal) -> {
                if (goal.getGoal() instanceof BreakDoorGoal) goal.stop();
            });
        super.updatePostDeath();
    }
}

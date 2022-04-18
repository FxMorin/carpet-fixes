package carpetfixes.patches;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

public class TouchWaterGoal extends SwimGoal {
    private final MobEntity mob;

    public TouchWaterGoal(MobEntity mob) {
        super(mob);
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.JUMP));
        mob.getNavigation().setCanSwim(true);
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public boolean canStart() {
        return this.mob.isTouchingWater() || this.mob.isInLava();
    }

    @Override
    public void tick() {
        this.mob.getJumpControl().setActive();
    }
}

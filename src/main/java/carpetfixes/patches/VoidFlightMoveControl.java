package carpetfixes.patches;

import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.mob.MobEntity;

import java.util.function.Supplier;

public class VoidFlightMoveControl extends FlightMoveControl {

    private final Supplier<Boolean> isRuleActive;

    public VoidFlightMoveControl(MobEntity entity, int maxPitchChange, boolean noGravity, Supplier<Boolean> rule) {
        super(entity, maxPitchChange, noGravity);
        this.isRuleActive = rule;
    }

    @Override
    public void tick() {
        if (isRuleActive.get() && this.entity.getY() <= this.entity.getWorld().getBottomY())
            this.entity.setNoGravity(false);
        super.tick();
    }
}

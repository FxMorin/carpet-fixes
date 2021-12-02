package carpetfixes.patches;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.mob.MobEntity;

public class BeeFlightMoveControl extends FlightMoveControl {

    public BeeFlightMoveControl(MobEntity entity, int maxPitchChange, boolean noGravity) {
        super(entity, maxPitchChange, noGravity);
    }

    @Override
    public void tick() {
        if (CarpetFixesSettings.beeStuckInVoidFix && this.entity.getY() <= this.entity.world.getBottomY()) this.entity.setNoGravity(false);
        super.tick();
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.VoidFlightMoveControl;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes Bees being unable to fly once they hit the void, resulting in them getting stuck
 */

@Mixin(BeeEntity.class)
public class BeeEntity_voidMixin {


    @Redirect(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/entity/ai/control/FlightMoveControl"
            ),
            require = 0
    )
    private FlightMoveControl cf$modifiedFlightController(MobEntity entity, int maxPitchChange, boolean noGravity) {
        return new VoidFlightMoveControl(entity, maxPitchChange, noGravity, () -> CFSettings.beeStuckInVoidFix);
    }
}

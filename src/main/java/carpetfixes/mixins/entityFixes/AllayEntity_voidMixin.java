package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.VoidFlightMoveControl;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AllayEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes Allays being unable to fly once they hit the void, resulting in them getting stuck
 */

@Mixin(AllayEntity.class)
public class AllayEntity_voidMixin {


    @Redirect(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/entity/ai/control/FlightMoveControl"
            ),
            require = 0
    )
    public FlightMoveControl cf$ModifiedFlightController(MobEntity entity, int maxPitchChange, boolean noGravity) {
        return new VoidFlightMoveControl(entity, maxPitchChange, noGravity, () -> CFSettings.allayStuckInVoidFix);
    }
}

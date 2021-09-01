package carpetfixes.mixins.entityFixes;

import carpetfixes.patches.BeeFlightMoveControl;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeeEntity.class)
public class BeeEntity_voidMixin {


    @Redirect(
            method="<init>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/entity/ai/control/FlightMoveControl"
            ))
    public FlightMoveControl ModifiedFlightController(MobEntity entity, int maxPitchChange, boolean noGravity) {
        return new BeeFlightMoveControl(entity,maxPitchChange,noGravity);
    }
}

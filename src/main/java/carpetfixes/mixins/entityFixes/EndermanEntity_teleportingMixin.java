package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndermanEntity.class)
public class EndermanEntity_teleportingMixin {
    @Redirect(method= "mobTick()V",at=@At(value="INVOKE",target="Lnet/minecraft/entity/mob/EndermanEntity;getBrightnessAtEyes()F"))
    public float brightnessAndMinecart(EndermanEntity entity) {
        if (CarpetFixesSettings.endermanUselessMinecartTeleportingFix && entity.hasVehicle()) {
            return 0.0f;
        }
        return entity.getBrightnessAtEyes();
    }
}

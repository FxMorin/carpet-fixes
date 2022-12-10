package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Since falling blocks override the tick() method, they "forgot" to add the nether portal ticking.
 * We also fix portal cooldown not being reset here
 */

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_portalsMixin extends Entity {

    public FallingBlockEntity_portalsMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    private void tickNetherPortal(CallbackInfo ci) {
        if (CFSettings.fallingBlocksCantUseNetherPortalsFix) {
            this.tickPortal();
        }
        if (CFSettings.fallingBlocksCantReuseGatewaysFix) {
            this.tickPortalCooldown();
        }
    }
}

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

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_portalsMixin extends Entity {

    /**
     * Since falling blocks override the tick() method, they "forgot" to add the nether portal ticking
     */


    public FallingBlockEntity_portalsMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    public void tickPortal(CallbackInfo ci) {
        if (CFSettings.fallingBlocksCantUseNetherPortalsFix) {
            this.tickPortal();
        } else if (CFSettings.fallingBlocksCantReuseGatewaysFix) {
            this.tickPortal(); // Improperly named, it's used for gateways too
        }
    }
}

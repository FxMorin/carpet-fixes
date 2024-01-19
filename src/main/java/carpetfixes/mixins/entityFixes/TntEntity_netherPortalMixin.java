package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Since TntEntity overrides the tick() method, they "forgot" to add the nether portal ticking
 */

@Mixin(TntEntity.class)
public abstract class TntEntity_netherPortalMixin extends Entity {

    public TntEntity_netherPortalMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    private void cf$tickNetherPortal(CallbackInfo ci) {
        if (CFSettings.tntCantUseNetherPortalsFix) {
            this.tickPortal();
        }
    }
}

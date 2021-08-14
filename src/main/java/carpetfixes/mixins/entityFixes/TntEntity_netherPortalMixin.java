package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntEntity.class)
public abstract class TntEntity_netherPortalMixin extends Entity  {

    /**
     * Since TntEntity overrides the tick() method, they forgot to add
     * the nether portal ticking, so that's what we do here.
     */


    public TntEntity_netherPortalMixin(EntityType<?> type, World world) { super(type, world); }


    @Inject(
            method= "tick()V",
            at=@At("HEAD")
    )
    public void tickNetherPortal(CallbackInfo ci) {
        if (CarpetFixesSettings.tntCantUseNetherPortalsFix) this.tickNetherPortal();
    }
}

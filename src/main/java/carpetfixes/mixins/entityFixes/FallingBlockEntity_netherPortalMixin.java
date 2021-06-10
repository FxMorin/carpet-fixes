package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_netherPortalMixin extends Entity {

    public FallingBlockEntity_netherPortalMixin(EntityType<?> type, World world) { super(type, world); }

    /**
     * Since falling blocks override the tick() method, they forgot to add
     * the nether portal ticking, so that's what we do here.
     */
    @Inject(method= "tick()V",at=@At("HEAD"))
    public void tickNetherPortal(CallbackInfo ci) {
        if (CarpetFixesSettings.fallingBlocksCantUseNetherPortalsFix) { this.tickNetherPortal(); }
    }
}

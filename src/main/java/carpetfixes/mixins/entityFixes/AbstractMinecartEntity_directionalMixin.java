package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static carpetfixes.helpers.Utils.isZero;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntity_directionalMixin extends Entity {

    public AbstractMinecartEntity_directionalMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /*
     * This is a hackfix, the current implementation of the minecart would require me to change the internal values
     * used for yaw in order to properly implement it. I might attempt to make the changes at some point, although
     * it's going to require a lot of mixins!
     */


    @Inject(
            method = "pushAwayFrom",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/util/math/Vec3d",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void pushAwayFromCorrectly(Entity entity, CallbackInfo ci) {
        if (CFSettings.directionalMinecartCollisionFix && isZero(this.getVelocity()) && isZero(entity.getVelocity()))
            ci.cancel();
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * When bee navigation is set to idle, they get downwards velocity and drop like boulders.
 * Just spawn a ton using bee spawn eggs and a dispenser and you will see it xD
 */

@Mixin(BeeEntity.class)
public abstract class BeeEntity_flyMixin extends AnimalEntity {

    protected BeeEntity_flyMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/AnimalEntity;tickMovement()V",
                    shift = At.Shift.AFTER
            )
    )
    private void dontDropLikeABoulder(CallbackInfo ci) {
        if (CFSettings.beesDropLikeBouldersFix && !onGround && this.getVelocity().y < 0.0 && this.navigation.isIdle())
            this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
    }
}

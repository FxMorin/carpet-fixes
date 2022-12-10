package carpetfixes.mixins.entityFixes.bucketableLeashDetach;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes leash not being detached when picking up an axolotl using a bucket
 */

@Mixin(AxolotlEntity.class)
public abstract class AxolotlEntity_leashDetachMixin extends AnimalEntity {

    protected AxolotlEntity_leashDetachMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "interactMob",
            at = @At("RETURN")
    )
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (CFSettings.bucketableMobsNotDetachingLeashesFix && this.isLeashed() && cir.getReturnValue().isAccepted())
            this.detachLeash(false,true);
    }
}

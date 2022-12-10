package carpetfixes.mixins.entityFixes.bucketableLeashDetach;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes leash not being detached when picking up a fish using a bucket
 *
 * Added to fish in-case some mod makes it possible to leash fish
 */

@Mixin(FishEntity.class)
public abstract class FishEntity_leashDetachMixin extends WaterCreatureEntity {

    protected FishEntity_leashDetachMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
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

package carpetfixes.mixins.entityFixes.bucketableLeashDetach;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.TadpoleEntity;
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
 *
 * Added to tadpole in-case some mod makes it possible to leash tadpoles
 */

@Mixin(TadpoleEntity.class)
public abstract class TadpoleEntity_leashDetachMixin extends FishEntity {

    public TadpoleEntity_leashDetachMixin(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "interactMob",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            )
    )
    private void cf$interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (CFSettings.bucketableMobsNotDetachingLeashesFix && this.isLeashed() && cir.getReturnValue().isAccepted()) {
            this.detachLeash(false, true);
        }
    }
}

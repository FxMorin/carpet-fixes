package carpetfixes.mixins.reIntroduced;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntity_portalGeneralItemMixin extends LivingEntity {

    /**
     * Remove the part where it sets all stack counts to 0, allows for some
     * interesting general item dupes to work. (Mostly just the dolphin one)
     */


    protected MobEntity_portalGeneralItemMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }


    @Inject(
            method="removeFromDimension",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/entity/mob/MobEntity;getItemsEquipped()Ljava/lang/Iterable;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    protected void reEnableGeneralItemDupe(CallbackInfo ci) {
        if (CarpetFixesSettings.reIntroducePortalGeneralItemDupe) ci.cancel();
    }
}
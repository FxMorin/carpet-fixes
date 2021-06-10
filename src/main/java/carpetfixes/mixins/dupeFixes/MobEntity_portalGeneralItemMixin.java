package carpetfixes.mixins.dupeFixes;

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
    protected MobEntity_portalGeneralItemMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

    /**
     * Remove the part where it sets all stack counts to 0, allows for some
     * interesting general item dupes to work.
     */
    @Inject(method="removeFromDimension",at=@At(value="INVOKE",target="Lnet/minecraft/entity/mob/MobEntity;getItemsEquipped()Ljava/lang/Iterable;"), cancellable = true)
    protected void reEnableGeneralItemDupe(CallbackInfo ci) {
        if (CarpetFixesSettings.portalGeneralItemDupeFix) {
            ci.cancel();
        }
    }
}
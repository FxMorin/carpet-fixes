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
abstract class MobEntity_portalGeneralItemMixin extends LivingEntity {
    protected MobEntity_portalGeneralItemMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method= "method_30076()V",at=@At("RETURN"))
    protected void stopGeneralItemDupes(CallbackInfo ci) {
        if (CarpetFixesSettings.portalGeneralItemDupeFix) {
            this.getItemsEquipped().forEach(lvt0 -> lvt0.setCount(0));
        }
    }
}

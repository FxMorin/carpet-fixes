package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntity_leashUpdateOrderMixin extends LivingEntity {

    /**
     * For this fix we simply move this.updateLeash() to happen first in tick()
     * since super.tick() sometimes does some leash checks such as isLeashed()
     * which return false in the first tick before the leash gets initialized
     * in this.updateLeash(), so we inject this.updateLeash() to the top, and
     * make the original do nothing :)
     */


    protected MobEntity_leashUpdateOrderMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }
    @Shadow protected void updateLeash() {}


    @Inject(
            method= "tick()V",
            at=@At("HEAD")
    )
    public void dontTickEarly(CallbackInfo ci) {
        if (CarpetFixesSettings.petsBreakLeadsDuringReloadFix && !this.world.isClient) this.updateLeash();
    }


    @Redirect(
            method= "tick()V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/entity/mob/MobEntity;updateLeash()V"
            ))
    public void weAlreadyUpdatedLeash(MobEntity mobEntity) {
        if (!CarpetFixesSettings.petsBreakLeadsDuringReloadFix) this.updateLeash();
    }
}

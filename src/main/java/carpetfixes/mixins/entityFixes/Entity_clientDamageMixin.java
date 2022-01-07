package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Entity.class)
public class Entity_clientDamageMixin {

    @Shadow public World world;

    private final Entity self = (Entity) (Object) this;


    @Inject(
            method = "isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isInvulnerableToAndClient(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetFixesSettings.clientSideDamageDesyncFix && (self instanceof ItemEntity || self instanceof ExperienceOrbEntity)) {
            cir.setReturnValue(true);
        }
    }
}

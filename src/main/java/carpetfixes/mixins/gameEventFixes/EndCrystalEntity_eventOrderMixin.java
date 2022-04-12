package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntity_eventOrderMixin {

    @Shadow
    protected abstract void crystalDestroyed(DamageSource source);


    @Redirect(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;kill()V"
            )
    )
    private void skipGameEventOnKill(Entity instance) {
        if (CFSettings.crystalExplosionGivesWrongEventFix) {
            this.crystalDestroyed(DamageSource.GENERIC);
            instance.remove(Entity.RemovalReason.KILLED);
        } else {
            instance.kill();
        }
    }
}

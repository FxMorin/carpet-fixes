package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

/**
 * Fixes armorstands not being able to ride minecarts
 */

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntity_armorStandMixin {


    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/predicate/entity/EntityPredicates;" +
                            "canBePushedBy(Lnet/minecraft/entity/Entity;)Ljava/util/function/Predicate;"
            )
    )
    private Predicate<Entity> canBePushedOrArmorStand(Entity entity) {
        Predicate<Entity> predicate = EntityPredicates.canBePushedBy(entity);
        return CFSettings.armorStandsCantRideVehiclesFix ?
                predicate.or(e -> e instanceof ArmorStandEntity) : predicate;
    }
}

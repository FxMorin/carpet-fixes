package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ShulkerEntity.class)
public class ShulkerEntity_vehicleOffsetMixin {


    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(
            method = "getHeightOffset()D",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;"
            ),
            name = "entityType"
    )
    public EntityType<?> modifyChestBoatToBoat(EntityType<?> type) {
        return CFSettings.shulkersAreLowerInChestBoatFix && type == EntityType.CHEST_BOAT ? EntityType.BOAT : type;
    }
}

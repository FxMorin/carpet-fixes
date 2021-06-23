package carpetfixes.mixins.dupeFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntity_giveMixin {

    @Inject(method="extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z",at=@At("HEAD"),cancellable = true)
    private static void extract(Inventory inventory, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetFixesSettings.giveCommandDupeFix && itemEntity.pickupDelay == 32767) {
            cir.setReturnValue(true);
        }
    }
}

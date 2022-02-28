package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntity_giveMixin {

    /**
     * This fixes the dupe glitch using /give (im not kidding)
     * When the command /give is executed, the server spawns an item entity at that player with un-pickup-able,
     * and it only lasts for around 1 tick. I believe it does this so other players hear the sound that someone picked
     * up an item. (ya ik, wth is going)
     * Anyways, mojang forgot to add the check to prevent picking up un-pickup-able items in the hopper code
     * (hopper & hopper minecart) Which means if your head is in a hopper or hopper minecart, you get the item twice,
     * because the hopper also get one. The fix is to add the correct check for the illegal item xDD
     * Seriously tho wth is that code, how on earth is this how that works. Geez
     */


    @Inject(
            method = "extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void extract(Inventory inventory, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.giveCommandDupeFix && itemEntity.pickupDelay == 32767) cir.setReturnValue(true);
    }
}

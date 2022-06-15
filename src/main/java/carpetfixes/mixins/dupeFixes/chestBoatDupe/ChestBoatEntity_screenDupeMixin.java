package carpetfixes.mixins.dupeFixes.chestBoatDupe;

import carpetfixes.CFSettings;
import carpetfixes.patches.linkedEntityInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChestBoatEntity.class)
public class ChestBoatEntity_screenDupeMixin {

    private final Entity self = (Entity)(Object)this;


    @Redirect(
            method = "createMenu",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/GenericContainerScreenHandler;createGeneric9x3(" +
                            "ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;)" +
                            "Lnet/minecraft/screen/GenericContainerScreenHandler;"
            )
    )
    public GenericContainerScreenHandler createMenu(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        if (CFSettings.chestBoatDupeFix) {
            GenericContainerScreenHandler screenHandler = GenericContainerScreenHandler.createGeneric9x3(
                    syncId, playerInventory, inventory
            );
            ((linkedEntityInventory)screenHandler).setLinkedEntity(self);
            return screenHandler;
        } else {
            return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
        }
    }
}

package carpetfixes.mixins.dupeFixes.chestBoatDupe;

import carpetfixes.CFSettings;
import carpetfixes.patches.linkedEntityInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GenericContainerScreenHandler.class)
public class GenericContainerScreenHandler_dupeMixin implements linkedEntityInventory {

    @Shadow
    @Final
    private Inventory inventory;

    @Nullable
    private Entity linkedEntity;

    @Override
    public void setLinkedEntity(@Nullable Entity linkedEntity) {
        this.linkedEntity = linkedEntity;
    }


    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true
    )
    public void canUseWithEntity(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.chestBoatDupeFix && this.linkedEntity != null) {
            boolean isValid = true;
            if (this.linkedEntity instanceof VehicleInventory vehicleInventory)
                isValid = vehicleInventory.canPlayerAccess(player);
            cir.setReturnValue(isValid && this.inventory.canPlayerUse(player) && this.linkedEntity.isAlive());
        }
    }


    @Inject(
            method = "transferSlot(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onlyTransferIfEntityAlive(PlayerEntity p, int i, CallbackInfoReturnable<ItemStack> cir) {
        if (CFSettings.chestBoatDupeFix && this.linkedEntity != null &&
                (p.isDead() || p.isRemoved() || !this.linkedEntity.isAlive() || this.linkedEntity.isRemoved()))
            cir.setReturnValue(ItemStack.EMPTY);
    }
}

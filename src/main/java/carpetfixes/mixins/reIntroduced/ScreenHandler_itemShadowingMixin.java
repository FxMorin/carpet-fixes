package carpetfixes.mixins.reIntroduced;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenHandler_itemShadowingMixin {

    private int lastButton = 0;


    @Redirect(
            method= "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice=@Slice(
                    from=@At(
                            value="FIELD",
                            target="Lnet/minecraft/screen/slot/SlotActionType;SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V",
                    ordinal = 1
            ),
            require = 0
    )
    private void dontRunBeforeInventoryUpdate(PlayerInventory instance, int slot, ItemStack stack) {
        if (!CarpetFixesSettings.reIntroduceItemShadowing) {
            instance.setStack(slot, stack);
        }
    }


    @Inject(
            method= "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice=@Slice(
                    from=@At(
                            value="FIELD",
                            target="Lnet/minecraft/screen/slot/SlotActionType;SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            ),
            require = 0
    )
    private void RunAfterInventoryUpdate(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (CarpetFixesSettings.reIntroduceItemShadowing) {
            player.getInventory().setStack(button, ItemStack.EMPTY);
        }
    }


    @Redirect(
            method= "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice=@Slice(
                    from=@At(
                            value="FIELD",
                            target="Lnet/minecraft/screen/slot/SlotActionType;SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V",
                    ordinal = 2
            ),
            require = 0
    )
    private void dontRunBeforeSecondInventoryUpdate(PlayerInventory instance, int slot, ItemStack stack) {
        if (!CarpetFixesSettings.reIntroduceItemShadowing) {
            instance.setStack(slot, stack);
        } else {
            lastButton = slot;
        }
    }


    @Redirect(
            method= "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice=@Slice(
                    from=@At(
                            value="FIELD",
                            target="Lnet/minecraft/screen/slot/SlotActionType;SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/screen/slot/Slot;onTakeItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)V",
                    ordinal = 2
            ),
            require = 0
    )
    private void RunAfterSecondInventoryUpdate(Slot instance, PlayerEntity player, ItemStack stack) {
        if (CarpetFixesSettings.reIntroduceItemShadowing) {
            player.getInventory().setStack(lastButton, stack);
        }
        instance.onTakeItem(player,stack);
    }
}

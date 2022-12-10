package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Adds back item shadowing! Why would mojang patch quantum mechanics in minecraft (*except for the 20 dupes it had*)
 * TODO: Currently broken, not sure why
 */

@Mixin(ScreenHandler.class)
public abstract class ScreenHandler_itemShadowingMixin {

    private int lastButton = 0;


    @Redirect(
            method = "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V",
                    ordinal = 1
            ),
            require = 0
    )
    private void dontRunBeforeInventoryUpdate(PlayerInventory instance, int slot, ItemStack stack) {
        if (!CFSettings.reIntroduceItemShadowing) instance.setStack(slot, stack);
    }


    @Inject(
            method = "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            ),
            require = 0
    )
    private void RunAfterInventoryUpdate(int slotIndex, int button, SlotActionType actionType,
                                         PlayerEntity player, CallbackInfo ci) {
        if (CFSettings.reIntroduceItemShadowing) player.getInventory().setStack(button, ItemStack.EMPTY);
    }


    @Redirect(
            method = "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;setStack(ILnet/minecraft/item/ItemStack;)V",
                    ordinal = 2
            ),
            require = 0
    )
    private void dontRunBeforeSecondInventoryUpdate(PlayerInventory instance, int slot, ItemStack stack) {
        if (!CFSettings.reIntroduceItemShadowing) {
            instance.setStack(slot, stack);
        } else {
            lastButton = slot;
        }
    }


    @Redirect(
            method = "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/Slot;onTakeItem(Lnet/minecraft/entity/player/PlayerEntity;" +
                            "Lnet/minecraft/item/ItemStack;)V",
                    ordinal = 2
            ),
            require = 0
    )
    private void RunAfterSecondInventoryUpdate(Slot instance, PlayerEntity player, ItemStack stack) {
        if (CFSettings.reIntroduceItemShadowing) player.getInventory().setStack(lastButton, stack);
        instance.onTakeItem(player,stack);
    }


    @Inject(
            method = "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "QUICK_MOVE:Lnet/minecraft/screen/slot/SlotActionType;",
                            ordinal = 1
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V",
                    ordinal = 2
            ),
            require = 0
    )
    private void runBeforeThirdInventoryUpdate(int slotIndex, int button, SlotActionType actionType,
                                               PlayerEntity player, CallbackInfo ci, PlayerInventory playerInventory,
                                               ClickType clickType, Slot slot, ItemStack itemStack,
                                               ItemStack itemStack5) {
        if (CFSettings.reIntroduceItemShadowing) slot.setStack(itemStack5);
    }


    @Redirect(
            method = "internalOnSlotClick(IILnet/minecraft/screen/slot/SlotActionType;" +
                    "Lnet/minecraft/entity/player/PlayerEntity;)V",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "QUICK_MOVE:Lnet/minecraft/screen/slot/SlotActionType;",
                            ordinal = 1
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/screen/slot/SlotActionType;" +
                                    "SWAP:Lnet/minecraft/screen/slot/SlotActionType;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V",
                    ordinal = 0
            ),
            require = 0
    )
    private void dontRunBeforeThirdInventoryUpdate(Slot slot, ItemStack stack) {
        if (!CFSettings.reIntroduceItemShadowing) slot.setStack(stack);
    }
}

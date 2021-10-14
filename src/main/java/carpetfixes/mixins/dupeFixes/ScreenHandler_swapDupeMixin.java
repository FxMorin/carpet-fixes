package carpetfixes.mixins.dupeFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ScreenHandler.class)
public class ScreenHandler_swapDupeMixin {


    @ModifyArg(
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
                    ordinal = 2
            ),
            index = 0
    )
    private ItemStack modifyStack(ItemStack stack) {
        return CarpetFixesSettings.swapGeneralItemDupeFix ? stack.split(stack.getCount()) : stack;
    }
}

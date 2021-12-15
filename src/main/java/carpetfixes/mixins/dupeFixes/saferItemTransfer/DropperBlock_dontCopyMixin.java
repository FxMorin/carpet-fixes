package carpetfixes.mixins.dupeFixes.saferItemTransfer;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.DropperBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(DropperBlock.class)
public class DropperBlock_dontCopyMixin {

    private static ItemStack stack = ItemStack.EMPTY;

    @Redirect(
            method = "dispense(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;",
                    ordinal = 0
            ))
    protected ItemStack shouldCopyFirst(ItemStack itemStack) {
        stack = itemStack.copy();
        return CarpetFixesSettings.saferItemTransfers ? itemStack : itemStack.copy();
    }


    @Redirect(
            method = "dispense(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"
            ))
    protected ItemStack shouldCopy(ItemStack itemStack) {
        return CarpetFixesSettings.saferItemTransfers ? stack : itemStack.copy();
    }
}

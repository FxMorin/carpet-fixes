package carpetfixes.mixins.dupeFixes.saferItemTransfer;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.DropperBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

/**
 * A smarter way to write the dropper code so that it doesn't needlessly copy item stacks around.
 * This prevents exploits such as item shadowing from being able to duplicate items using droppers.
 */

@Mixin(DropperBlock.class)
public class DropperBlock_dontCopyMixin {


    @Redirect(
            method = "dispense(Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            slice = @Slice(
                    from = @At("HEAD"),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z",
                            ordinal = 1
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;",
                    ordinal = 0
            )
    )
    private ItemStack cf$shouldCopyFirst(ItemStack itemStack, @Share("stack") LocalRef<ItemStack> stackRef) {
        stackRef.set(itemStack.copy());
        return CFSettings.saferItemTransfers ? itemStack : stackRef.get();
    }


    @Redirect(
            method = "dispense(Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/item/ItemStack;isEmpty()Z",
                            ordinal = 1
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"
            )
    )
    private ItemStack cf$shouldCopy(ItemStack itemStack, @Share("stack") LocalRef<ItemStack> stackRef) {
        return CFSettings.saferItemTransfers ? stackRef.get() : itemStack.copy();
    }
}

package carpetfixes.mixins.dupeFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlock_breakSwapDupeMixin {
    /**
     * Clear the shulker box inventory after its stacks are dropped
     * This might break custom loot tables that intentionally duplicate items
     * but fixes other dupes in vanilla
     */
    @Inject(method = "method_10524", at = @At("TAIL"), remap = false)
    private static void clearAfterGetDroppedStack(ShulkerBoxBlockEntity shulkerBox, LootContext lootContext, Consumer<ItemStack> consumer, CallbackInfo ci) {
        if (CarpetFixesSettings.breakSwapGeneralItemDupeFix) {
            shulkerBox.clear();
        }
    }
}

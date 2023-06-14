package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * Clear the shulker box inventory after its stacks are dropped
 * This might break custom loot tables that intentionally duplicate items but fixes other dupes in vanilla
 */

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlock_breakSwapDupeMixin {

    @Inject(
            method = "method_10524",
            at = @At("TAIL"),
            remap = false
    )
    private static void clearAfterGetDroppedStack(ShulkerBoxBlockEntity shulkerBoxBlockEntity,
                                                  Consumer lootConsumer, CallbackInfo ci) {
        if (CFSettings.breakSwapGeneralItemDupeFix) shulkerBoxBlockEntity.clear();
    }
}

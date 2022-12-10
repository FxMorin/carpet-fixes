package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Add missing observer updates when a brewing stand finishes brewing
 */

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntity_updateMixin {


    @Inject(
            method = "craft(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/util/collection/DefaultedList;)V",
            at = @At("TAIL")
    )
    private static void onCraft(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci) {
        if (CFSettings.missingObserverUpdatesFix) Utils.giveObserverUpdates(world, pos);
    }
}

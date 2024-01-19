package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The item frame plays sound whenever its loaded, this is one of the funniest bugs out there.
 * Easily reproducible by making a large area full of item frames in a superflat world and loading the world.
 */

@Mixin(ItemFrameEntity.class)
public class ItemFrameEntity_soundMixin {


    @Inject(
            method = "setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/data/DataTracker;" +
                            "set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void cf$setHeldItemStackBefore(ItemStack value, boolean update, CallbackInfo ci) {
        if (CFSettings.itemFramePlaysSoundOnReadFix && !update) {
            value.setCount(0);
        }
    }


    @Inject(
            method = "setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V",
            at = @At("RETURN")
    )
    private void cf$setHeldItemStackAfter(ItemStack value, boolean update, CallbackInfo ci) {
        if (CFSettings.itemFramePlaysSoundOnReadFix && !update) {
            value.setCount(1);
        }
    }
}

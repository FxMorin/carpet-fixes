package carpetfixes.mixins.itemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStack_repairCostMixin {

    @Shadow public boolean hasTag() { return false; }
    @Shadow private CompoundTag tag;

    private int getHideFlags() {
        return this.hasTag() && this.tag.contains("HideFlags", 99) ? this.tag.getInt("HideFlags") : 0;
    }

    @Inject(method = "setRepairCost(I)V", at = @At("HEAD"), cancellable = true)
    public void setRepairCost(int repairCost, CallbackInfo ci) {
        if (CarpetFixesSettings.repairCostItemNotStackingFix && (this.getHideFlags() & ItemStack.TooltipSection.CAN_PLACE.getFlag()) == 0) {
            ci.cancel();
        }
    }
}

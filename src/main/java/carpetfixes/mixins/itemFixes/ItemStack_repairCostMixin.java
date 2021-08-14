package carpetfixes.mixins.itemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStack_repairCostMixin {

    /**
     * I use a very interesting way of getting around the problem here. I look for
     * the hidden tag called CAN_PLACE and if I find it, then it means that the
     * block can be placed, so the repairCost tag can be removed from it by just
     * placing it on the ground, so don't add a repairCost nbt tag to it,
     * since it's useless.
     */


    @Shadow public boolean hasTag() { return false; }
    @Shadow private NbtCompound tag;


    private int getHideFlags() {
        return this.hasTag() && this.tag.contains("HideFlags", 99) ? this.tag.getInt("HideFlags") : 0;
    }


    @Inject(
            method = "setRepairCost(I)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void setRepairCost(int repairCost, CallbackInfo ci) {
        if (CarpetFixesSettings.repairCostItemNotStackingFix && (this.getHideFlags() & ItemStack.TooltipSection.CAN_PLACE.getFlag()) == 0) {
            ci.cancel();
        }
    }
}

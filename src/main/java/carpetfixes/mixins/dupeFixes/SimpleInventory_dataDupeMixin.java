package carpetfixes.mixins.dupeFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleInventory.class)
public class SimpleInventory_dataDupeMixin {

    @Shadow public void setStack(int slot, ItemStack stack) {}
    @Shadow public int size() {
        return 0;
    }

    @Inject(method= "readNbtList(Lnet/minecraft/nbt/NbtList;)V",at=@At("HEAD"))
    public void readNbtListWithoutDupe(NbtList nbtList, CallbackInfo ci) {
        if (CarpetFixesSettings.nbtDataDupeFix) {
            for(int j = 0; j < this.size(); ++j) {
                this.setStack(j, ItemStack.EMPTY);
            }
        }
    }
}

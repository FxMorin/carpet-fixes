package carpetfixes.mixins.itemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemStack.class)
public class ItemStack_nbtSuffixMixin {


    @ModifyArg(
            method = "getRepairCost()I",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;I)Z"
            ),
            index = 1
    )
    private int incorrectNbtCheck(int value) {return CarpetFixesSettings.incorrectNbtChecks ? 99 : 3;}
}

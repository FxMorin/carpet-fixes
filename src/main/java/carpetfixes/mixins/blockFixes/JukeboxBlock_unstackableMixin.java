package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(JukeboxBlock.class)
public class JukeboxBlock_unstackableMixin {
    /*@Redirect(method="onPlaced", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;getOrCreateNbt()Lnet/minecraft/nbt/NbtCompound;"))
    private NbtCompound returnFakeNbtIfNotSet(ItemStack stack) {
        if (!CarpetFixesSettings.unstackableJukeboxFix) return stack.getOrCreateNbt();
        NbtCompound nbt = stack.getNbt();
        if (nbt != null) return nbt;
        return new NbtCompound();
    }*/
}

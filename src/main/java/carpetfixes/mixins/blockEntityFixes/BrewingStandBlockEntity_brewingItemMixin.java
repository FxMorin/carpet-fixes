package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntity_brewingItemMixin {

    @Shadow
    private Item itemBrewing;

    // Code Analysis by: Marcono1234
    // Won't work if only 1 fuel was used and it was used up, although seems intended...


    @Inject(
            method = "readNbt",
            at = @At("TAIL")
    )
    private void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if (CFSettings.brewingResetsOnUnloadFix)
            this.itemBrewing = Registry.ITEM.get(new Identifier(nbt.getString("itemBrewing")));
    }


    @Inject(
            method = "writeNbt",
            at = @At("TAIL")
    )
    private void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        if (CFSettings.brewingResetsOnUnloadFix)
            nbt.putString("itemBrewing", Registry.ITEM.getId(this.itemBrewing).toString());
    }
}

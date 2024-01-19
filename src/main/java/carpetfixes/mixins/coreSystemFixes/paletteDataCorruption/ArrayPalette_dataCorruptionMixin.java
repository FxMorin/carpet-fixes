package carpetfixes.mixins.coreSystemFixes.paletteDataCorruption;

import carpetfixes.CFSettings;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.world.chunk.ArrayPalette;
import net.minecraft.world.chunk.Palette;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes a data corruption bug in the Palette code.
 * It's caused by the listeners being copied over to the palette clone when instead a new listener should be made
 */

@Mixin(ArrayPalette.class)
public class ArrayPalette_dataCorruptionMixin<T> {

    @Shadow
    @Final
    private IndexedIterable<T> idList;

    @Shadow
    @Final
    private T[] array;

    @Shadow
    @Final
    private int indexBits;

    @Shadow
    private int size;


    @Inject(
            method = "copy",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$copyWithoutListener(CallbackInfoReturnable<Palette<T>> cir) {
        if (CFSettings.paletteCopyDataCorruptionFix) {
            cir.setReturnValue(new ArrayPalette<>(idList, array.clone(), (newSize, added) -> 0, indexBits, size));
        }
    }
}

package carpetfixes.mixins.coreSystemFixes.paletteDataCorruption;

import carpetfixes.CFSettings;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.world.chunk.ArrayPalette;
import net.minecraft.world.chunk.Palette;
import net.minecraft.world.chunk.PaletteResizeListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    private final PaletteResizeListener<T> dummyListener = (newSize, added) -> 0;


    @Inject(
            method = "copy",
            at = @At("HEAD"),
            cancellable = true
    )
    private void copyWithoutListener(CallbackInfoReturnable<Palette<T>> cir) {
        if (CFSettings.paletteCopyDataCorruptionFix)
            cir.setReturnValue(new ArrayPalette<>(idList, array.clone(), dummyListener, indexBits, size));
    }
}

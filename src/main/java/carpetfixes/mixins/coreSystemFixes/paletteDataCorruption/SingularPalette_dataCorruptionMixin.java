package carpetfixes.mixins.coreSystemFixes.paletteDataCorruption;

import carpetfixes.CFSettings;
import carpetfixes.mixins.accessors.SingularPaletteAccessor;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.world.chunk.Palette;
import net.minecraft.world.chunk.PaletteResizeListener;
import net.minecraft.world.chunk.SingularPalette;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Fixes a data corruption bug in the Palette code.
 * It's caused by the listeners being copied over to the palette clone when instead a new listener should be made
 */

@Mixin(SingularPalette.class)
public class SingularPalette_dataCorruptionMixin<T> {

    @Shadow
    @Final
    private IndexedIterable<T> idList;

    @Shadow
    private @Nullable T entry;

    private final PaletteResizeListener<T> dummyListener = (newSize, added) -> 0;


    @SuppressWarnings("unchecked")
    @Inject(
            method = "copy",
            at = @At("HEAD"),
            cancellable = true
    )
    private void copyWithoutListener(CallbackInfoReturnable<Palette<T>> cir) {
        if (CFSettings.paletteCopyDataCorruptionFix) {
            SingularPalette<T> singularPalette = new SingularPalette<>(idList, dummyListener, List.of());
            ((SingularPaletteAccessor<T>)singularPalette).setEntry(entry);
            cir.setReturnValue(singularPalette);
        }
    }
}

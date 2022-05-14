package carpetfixes.mixins.coreSystemFixes.paletteDataCorruption;

import carpetfixes.CFSettings;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.collection.Int2ObjectBiMap;
import net.minecraft.world.chunk.BiMapPalette;
import net.minecraft.world.chunk.Palette;
import net.minecraft.world.chunk.PaletteResizeListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiMapPalette.class)
public class BiMapPalette_dataCorruptionMixin<T> {

    @Shadow
    @Final
    private IndexedIterable<T> idList;

    @Shadow
    @Final
    private int indexBits;

    @Shadow
    @Final
    private Int2ObjectBiMap<T> map;

    private final PaletteResizeListener<T> dummyListener = (newSize, added) -> 0;


    @Inject(
            method = "copy",
            at = @At("HEAD"),
            cancellable = true
    )
    private void copyWithoutListener(CallbackInfoReturnable<Palette<T>> cir) {
        if (CFSettings.paletteCopyDataCorruptionFix)
            cir.setReturnValue(new BiMapPalette<>(idList, indexBits, dummyListener, map.copy()));
    }
}

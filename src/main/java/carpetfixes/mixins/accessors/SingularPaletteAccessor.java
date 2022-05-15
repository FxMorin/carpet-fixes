package carpetfixes.mixins.accessors;

import net.minecraft.world.chunk.SingularPalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingularPalette.class)
public interface SingularPaletteAccessor<T> {
    @Accessor("entry")
    void setEntry(T entry);
}

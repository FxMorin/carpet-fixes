package carpetfixes.mixins.accessors;

import com.google.common.collect.Interner;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TagKey.class)
public interface TagKeyAccessor {
    @Accessor("INTERNER")
    static Interner<TagKey<?>> getInterner() {
        throw new AssertionError();
    }
    @Accessor("INTERNER") @Mutable
    static void setInterner(Interner<TagKey<?>> interner) {
        throw new AssertionError();
    }
}

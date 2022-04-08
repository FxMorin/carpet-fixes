package carpetfixes.mixins.accessors;

import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import com.google.common.collect.Interner;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.GT_22w05a))
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

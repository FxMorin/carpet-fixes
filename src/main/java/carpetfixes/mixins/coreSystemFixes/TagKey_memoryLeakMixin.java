package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TagKey.class)
public class TagKey_memoryLeakMixin {


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/Interners;newStrongInterner()" +
                            "Lcom/google/common/collect/Interner;"
            )
    )
    private static Interner<TagKey<?>> makeWeakInterner() {
        if (CFSettings.tagKeyMemoryLeakFix) {
            return Interners.newWeakInterner();
        } else {
            return Interners.newStrongInterner();
        }
    }
}

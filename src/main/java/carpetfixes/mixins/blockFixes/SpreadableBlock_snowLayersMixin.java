package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpreadableBlock.class)
public class SpreadableBlock_snowLayersMixin {


    @Redirect(
            method = "canSurvive",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;" +
                            "get(Lnet/minecraft/state/property/Property;)Ljava/lang/Comparable;"
            )
    )
    private static Comparable<Integer> allowUpTo7(BlockState instance, Property<Integer> property) {
        int layers = instance.get(property);
        return CFSettings.grassSnowLayersFix && layers < 8 ? 1 : layers;
    }
}

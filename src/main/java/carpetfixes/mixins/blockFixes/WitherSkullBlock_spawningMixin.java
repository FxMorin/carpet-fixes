package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.WitherSkullBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Allows replaceable materials to be within the wither structure when the wither is spawning
 */

@Mixin(WitherSkullBlock.class)
public class WitherSkullBlock_spawningMixin {


    @Redirect(
            method = "method_51174",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
            )
    )
    private static boolean replaceableMaterialPredicate(BlockState state) {
        return CFSettings.witherGolemSpawningFix ? state.isReplaceable() : state.isAir();
    }


    @Redirect(
            method = "method_51175",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
            )
    )
    private static boolean replaceableMaterialPredicateDispenser(BlockState state) {
        return CFSettings.witherGolemSpawningFix ? state.isReplaceable() : state.isAir();
    }
}

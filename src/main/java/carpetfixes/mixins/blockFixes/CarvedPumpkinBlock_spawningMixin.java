package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarvedPumpkinBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Allows replaceable materials to be within the iron golem structure when the iron golem attempts to spawn.
 */
@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlock_spawningMixin {


    @Redirect(
            method = "method_51167",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
            )
    )
    private static boolean replaceableMaterialPredicate(BlockState state) {
        return CFSettings.witherGolemSpawningFix ? state.isReplaceable() : state.isAir();
    }


    @Redirect(
            method = "method_51168",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isAir()Z"
            )
    )
    private static boolean replaceableMaterialPredicateDispenser(BlockState state) {
        return CFSettings.witherGolemSpawningFix ? state.isReplaceable() : state.isAir();
    }
}

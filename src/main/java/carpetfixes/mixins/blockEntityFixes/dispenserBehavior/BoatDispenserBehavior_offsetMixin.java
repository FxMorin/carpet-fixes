package carpetfixes.mixins.blockEntityFixes.dispenserBehavior;

import carpetfixes.CFSettings;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Fixes boats placement when using a dispenser
 */
@Mixin(BoatDispenserBehavior.class)
public class BoatDispenserBehavior_offsetMixin {


    @ModifyConstant(
            method = "dispenseSilently",
            constant = @Constant(
                    doubleValue = 0.5625,
                    ordinal = 0
            )
    )
    private double spawnFurther(double constant) {
        return CFSettings.boatTooFarFromDispenserFix ? 0.50001 : constant;
    }
}

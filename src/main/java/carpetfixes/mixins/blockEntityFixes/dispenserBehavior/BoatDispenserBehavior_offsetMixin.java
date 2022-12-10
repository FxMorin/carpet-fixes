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
            constant = @Constant(doubleValue = 1.0)
    )
    private double offsetLower(double constant) {
        return CFSettings.boatsCreatedTooHighFix ? 0.5 : constant;
    }


    @ModifyConstant(
            method = "dispenseSilently",
            constant = @Constant(
                    floatValue = 1.125F,
                    ordinal = 0
            )
    )
    private float spawnFurtherX(float constant) {
        return CFSettings.boatsStuckInDispensersFix ? 1.2F : constant;
    }


    @ModifyConstant(
            method = "dispenseSilently",
            constant = @Constant(
                    floatValue = 1.125F,
                    ordinal = 2
            )
    )
    private float spawnFurtherZ(float constant) {
        return CFSettings.boatsStuckInDispensersFix ? 1.2F : constant;
    }
}

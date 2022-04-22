package carpetfixes.mixins.blockEntityFixes.dispenserBehavior;

import carpetfixes.CFSettings;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BoatDispenserBehavior.class)
public class BoatDispenserBehavior_offsetMixin {


    @ModifyConstant(
            method = "dispenseSilently",
            constant = @Constant(doubleValue = 1.0)
    )
    private double offsetLower(double constant) {
        return CFSettings.boatsCreatedTooHighFix ? 0.5 : constant;
    }
}

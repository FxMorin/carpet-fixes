package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SculkSensorBlockEntity.class)
public class SculkSensorBlockEntity_roundMixin {


    /**
     * @author FX - PR0CESS, ncolyer11
     * @reason Cause I want to... why am I forced to write this
     */
    @Overwrite
    public static int getPower(int distance, int range) {
        return CarpetFixesSettings.sculkSensorPrecisionLossFix ? MathHelper.floor((double)distance*-1.87375d+15.49d) : Math.max(1,15-MathHelper.floor(((double)distance / (double)range)*15.0D));
    }
}

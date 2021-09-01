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
     * @reason Cause I want to... why am I forced to write these
     */
    @Overwrite
    public static int getPower(int distance, int range) {
        double d = (double)distance / (double)range;
        return CarpetFixesSettings.sculkSensorPrecisionLossFix ? MathHelper.floor(d*-1.75d+15.5d) : Math.max(1,15-MathHelper.floor(d*15.0D));
    }
}

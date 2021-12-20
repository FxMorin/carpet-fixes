package carpetfixes.mixins.optimizations;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalLong;

@Mixin(DimensionType.class)
public abstract class DimensionType_preCalcMixin {

    //@Shadow public abstract boolean hasFixedTime();

    //@Shadow @Final private OptionalLong fixedTime;
    //private final float[] preCalculatedAngles = new float[this.hasFixedTime() ? 1 : 24000];

    /*private float cacheSkyAngle(long time) { //168000
        double d = MathHelper.fractionalPart((double)this.fixedTime.orElse(time) / 24000.0D - 0.25D);
        double e = 0.5D - Math.cos(d * 3.141592653589793D) / 2.0D;
        return (float)(d * 2.0D + e) / 3.0F;
    }*/


    /*@Inject(
            method = "<init>(Ljava/util/OptionalLong;ZZZZDZZZZZIIILnet/minecraft/util/Identifier;Lnet/minecraft/util/Identifier;F)V",
            at = @At("TAIL")
    )
    private void DimensionType(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean hasEnderDragonFight, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int minimumY, int height, int logicalHeight, Identifier infiniburn, Identifier effects, float ambientLight, CallbackInfo ci) {
        for (int i = 0; i < this.preCalculatedAngles.length; i++) {
            preCalculatedAngles[i] = cacheSkyAngle(i);
        }
    }*/


    /*@Inject(
            method = "getSkyAngle(J)F",
            at = @At("HEAD")
    )
    public void getSkyAngle(long time, CallbackInfoReturnable<Float> cir) {
        if (preCalculatedAngles[(int)time] != 0) {
            cir.setReturnValue(preCalculatedAngles[(int)time]);
        } else {
            double d = MathHelper.fractionalPart((double) this.fixedTime.orElse(time) / 24000.0D - 0.25D);
            double e = 0.5D - Math.cos(d * 3.141592653589793D) / 2.0D;
            float result = (float) (d * 2.0D + e) / 3.0F;
            preCalculatedAngles[(int)time] = result;
            cir.setReturnValue(result);
        }
    }*/
}

package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/gen/densityfunction/DensityFunctionTypes$EndIslands")
public class DensityFunctionTypes$EndIslands_voidRingsMixin {

    /**
     * In the end, there are large rings where terrain does not generate. This is due to a floating-point calculation.
     * Original code by: Shadew, I then improved performance a bit. Not only does this fix the end void rings,
     * but the equation is actually much faster than the vanilla equation, without changing vanilla behaviour!
     */


    @Inject(
            method = "method_41529(Lnet/minecraft/util/math/noise/SimplexNoiseSampler;II)F",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getModifiedNoiseAt(SimplexNoiseSampler simplexNoiseSampler,
                                           int x, int z, CallbackInfoReturnable<Float> cir) {
        if (CFSettings.endVoidRingsFix) {
            int chunkX = x / 2, chunkZ = z / 2, chunkSectionX = x % 2, chunkSectionZ = z % 2;
            float noiseShift = (MathHelper.abs(x) < 400 && MathHelper.abs(z) < 400) ?
                    MathHelper.clamp(400 - MathHelper.sqrt(x * x + z * z) * 8, -100, 80) :
                    -100;
            for (int islandX = -12; islandX <= 12; ++islandX) {
                long areaX = (chunkX + islandX);
                float seedX = (chunkSectionX-islandX*2);
                for (int islandZ = -12; islandZ <= 12; ++islandZ) {
                    long areaZ = (chunkZ + islandZ);
                    if (areaX * areaX + areaZ * areaZ > 4096L && simplexNoiseSampler.sample(areaX, areaZ) < -0.9F) {
                        float seedZ = (chunkSectionZ-islandZ*2);
                        float shiftPiece = MathHelper.abs(areaX) * 3439.0F + MathHelper.abs(areaZ) * 147.0F;
                        float offset = shiftPiece % 13.0F + 9.0F;
                        float shiftOffset = 100.0F - MathHelper.sqrt(seedX*seedX+seedZ*seedZ) * offset;
                        float maxShift = MathHelper.clamp(shiftOffset, -100.0F, 80.0F);
                        noiseShift = Math.max(noiseShift, maxShift);
                    }
                }
            }
            cir.setReturnValue(noiseShift);
        }
    }
}

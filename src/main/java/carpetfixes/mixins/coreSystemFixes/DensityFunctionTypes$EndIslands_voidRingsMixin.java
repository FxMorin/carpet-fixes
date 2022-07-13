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
            float noiseShift = (MathHelper.abs(chunkX) < 200 && MathHelper.abs(chunkZ) < 200) ?
                    MathHelper.clamp(100.0F - MathHelper.sqrt(x * x + z * z) * 8.0F, -100.0F, 80.0F) :
                    -100;
            for (int islandX = -12; islandX <= 12; ++islandX) {
                long areaX = (chunkX + islandX);
                for (int islandZ = -12 + Math.max(islandX-4, 0); islandZ <= 12 + Math.min(islandX+4, 0); ++islandZ) {
                    long areaZ = (chunkZ + islandZ);
                    if (areaX * areaX + areaZ * areaZ > 4096L && simplexNoiseSampler.sample(areaX, areaZ) < -0.9F) {
                        float seedX = chunkSectionX - islandX * 2;
                        float seedZ = chunkSectionZ - islandZ * 2;
                        float sqrtSeed = MathHelper.sqrt(seedX * seedX + seedZ * seedZ);
                        if (sqrtSeed * 9 < (noiseShift * -1) + 100.0005) {
                            float areaWeight = MathHelper.abs(areaX) * 3439.0F + MathHelper.abs(areaZ) * 147.0F;
                            float offset = areaWeight % 13.0F + 9.0F;
                            float maxShift = MathHelper.clamp(100.0F - sqrtSeed * offset, -100.0F, 80.0F);
                            noiseShift = Math.max(noiseShift, maxShift);
                        }
                    }
                }
            }
            cir.setReturnValue(noiseShift);
        }
    }
}

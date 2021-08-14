package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TheEndBiomeSource.class)
public class TheEndBiomeSource_endVoidMixin {

    /**
     * In the end, there are large rings where terrain does not generate. This is due
     * to a floating-point calculation. Original code by: Shadew, I then improved
     * performance a bit. Not only does this fix the end void rings, but the equation
     * is actually much faster than the vanilla equation, without changing vanilla
     * behaviour!
     */


    @Inject(
            method= "getNoiseAt(Lnet/minecraft/util/math/noise/SimplexNoiseSampler;II)F",
            at=@At("HEAD"),
            cancellable = true
    )
    private static void getNoiseAt(SimplexNoiseSampler simplexNoiseSampler, int x, int z, CallbackInfoReturnable<Float> cir) {
        if (CarpetFixesSettings.endVoidRingsFix) {
            int chunkX = x/2, chunkZ = z/2, chunkSectionX = x%2, chunkSectionZ = z%2;
            float noiseShift = (MathHelper.abs(x) < 400 && MathHelper.abs(z) < 400) ? MathHelper.clamp(400 - MathHelper.sqrt(x * x + z * z) * 8, -100, 80) : -100;
            for (int islandX = -12; islandX <= 12; ++islandX) {
                long areaX = (chunkX + islandX);
                for (int islandZ = -12; islandZ <= 12; ++islandZ) {
                    long areaZ = (chunkZ + islandZ);
                    if (areaX * areaX + areaZ * areaZ > 4096L && simplexNoiseSampler.sample(areaX, areaZ) < -0.8999999761581421D) {
                        float seedX = (chunkSectionX-islandX*2), seedZ = (chunkSectionZ-islandZ*2);
                        noiseShift = Math.max(noiseShift, MathHelper.clamp(100.0F-MathHelper.sqrt(seedX*seedX+seedZ*seedZ)*((MathHelper.abs(areaX)*3439.0F+MathHelper.abs(areaZ)*147.0F)%13.0F+9.0F), -100.0F, 80.0F));
                    }
                }
            }
            cir.setReturnValue(noiseShift);
        }
    }
}

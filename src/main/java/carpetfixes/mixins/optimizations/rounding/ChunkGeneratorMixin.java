package carpetfixes.mixins.optimizations.rounding;

import carpetfixes.CFSettings;
import carpetfixes.helpers.FastMath;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ChunkGenerator.class, priority = 1010)
public class ChunkGeneratorMixin {


    @Redirect(
            method = "generateStrongholdPositions()V",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;round(D)J"
            )
    )
    private long fasterRound(double value) {
        return CFSettings.optimizedRounding ? FastMath.round(value) : Math.round(value);
    }
}

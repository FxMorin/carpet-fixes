package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.SculkSensorListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SculkSensorListener.class)
public class SculkSensorListener_betterRaycastMixin {

    /**
     * Sculk sensor has a directional bias when it comes to wool occlusion. This is due the raycast going at a perfect
     * 45 degree, which means that when it hits the corner of 2 blocks at the same time, it is only able to pick one.
     * This fix could be integrated directly into the world raycast, performance changes are unnoticeable.
     *
     * How it works:
     * We simply check if both the X & Z offsets are the same (abs), if they are it means that this is a perfect
     * diagonal (45 degree angle). In which case, we can later do a secondary check if on the second block by flipping
     * the X & Z offsets. Since this only happens on 45 degree angles, switching X & Z will not cause any issues!
     */


    @Redirect(
            method = "isOccluded(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;raycast(Lnet/minecraft/world/BlockStateRaycastContext;)Lnet/minecraft/util/hit/BlockHitResult;"
            )
    )
    private BlockHitResult isOccluded(World world, BlockStateRaycastContext context) {
        return CarpetFixesSettings.sculkSensorBiasFix ? Utils.raycast(world, context) : world.raycast(context);
    }

}

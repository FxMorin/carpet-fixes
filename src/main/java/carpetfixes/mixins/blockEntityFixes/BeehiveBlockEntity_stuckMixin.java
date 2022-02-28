package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntity_stuckMixin {


    @Redirect(
            method = "releaseBee",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isRaining()Z"
            )
    )
    private static boolean isRainingBetter(World world) {
        return CFSettings.beeNotLeavingHiveFix ?
                !world.getDimension().hasFixedTime() && world.getDimension().hasSkyLight() && world.isRaining() :
                world.isRaining();
    }
}

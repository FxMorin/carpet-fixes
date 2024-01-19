package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes bees not leaving there hive in the nether, due to the isRaining tag being checked without a dimension check
 */
@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntity_stuckMixin {


    @Redirect(
            method = "releaseBee",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isRaining()Z"
            )
    )
    private static boolean cf$isRainingBetter(World world) {
        return CFSettings.beeNotLeavingHiveFix ?
                !world.getDimension().hasFixedTime() && world.getDimension().hasSkyLight() && world.isRaining() :
                world.isRaining();
    }
}

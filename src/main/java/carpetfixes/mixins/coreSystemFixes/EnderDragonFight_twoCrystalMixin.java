package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Fixes a bug in the distance calculation allowing you to spawn the ender dragon with only 2 end crystals instead of 4
 */

@Mixin(EnderDragonFight.class)
public class EnderDragonFight_twoCrystalMixin {


    @ModifyArg(
            method = "respawnDragon()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;offset(Lnet/minecraft/util/math/Direction;I)" +
                            "Lnet/minecraft/util/math/BlockPos;"
            ),
            index = 1
    )
    private int cf$modifyDist(int dist) {
        return CFSettings.respawnDragonWithoutAllEndCrystalsFix ? 3 : dist;
    }
}

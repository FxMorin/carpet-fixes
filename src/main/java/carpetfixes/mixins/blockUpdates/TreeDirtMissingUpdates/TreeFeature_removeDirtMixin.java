package carpetfixes.mixins.blockUpdates.TreeDirtMissingUpdates;

import carpetfixes.CFSettings;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

/**
 * Grabs the last dirt positions and removes them from the trunk positions so that no incorrect updates are made
 */

@Mixin(TreeFeature.class)
public class TreeFeature_removeDirtMixin {


    @Inject(
            method = "placeLogsAndLeaves(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockBox;" +
                    "Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)Lnet/minecraft/util/shape/VoxelSet;",
            at = @At("HEAD")
    )
    private static void cf$placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> trunkPositions,
                                              Set<BlockPos> decorationPositions, Set<BlockPos> set,
                                              CallbackInfoReturnable<VoxelSet> cir) {
        if (CFSettings.treeTrunkLogicFix) {
            trunkPositions.removeAll(CFSettings.LAST_DIRT.get());
        }
        CFSettings.LAST_DIRT.get().clear();
    }
}

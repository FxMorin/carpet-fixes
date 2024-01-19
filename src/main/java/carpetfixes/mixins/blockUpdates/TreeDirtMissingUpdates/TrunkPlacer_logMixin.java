package carpetfixes.mixins.blockUpdates.TreeDirtMissingUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

/**
 * Adds the last dirt blocks to a set so they can be skipped later
 */

@Mixin(TrunkPlacer.class)
public class TrunkPlacer_logMixin {


    @Inject(
            method = "setToDirt(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                    "Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)V",
            at = @At("HEAD")
    )
    private static void cf$setToDirtNotTrunk(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                             Random random, BlockPos pos, TreeFeatureConfig config,
                                             CallbackInfo ci) {
        CFSettings.LAST_DIRT.get().add(pos);
    }
}
package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BiConsumer;

/**
 * Fixes giant trunks having an extra log at the very top
 */

@Mixin(GiantTrunkPlacer.class)
public abstract class GiantTrunkPlacer_extraWoodMixin {

    @Shadow
    protected abstract void setLog(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer,
                                   Random random, BlockPos.Mutable mutable,
                                   TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, int k);


    @Redirect(
            method = "generate(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                    "Lnet/minecraft/util/math/random/Random;ILnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/trunk/GiantTrunkPlacer;setLog(" +
                            "Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                            "Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos$Mutable;" +
                            "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;" +
                            "Lnet/minecraft/util/math/BlockPos;III)V",
                    ordinal = 1
            )
    )
    private void cf$addSetLog(GiantTrunkPlacer placer, TestableWorld world,
                              BiConsumer<BlockPos, BlockState> biConsumer, Random random, BlockPos.Mutable mutable,
                              TreeFeatureConfig config, BlockPos blockPos, int i, int j, int k) {
        if (CFSettings.giantTreesHaveExtraLogFix) {
            this.setLog(
                    world,
                    biConsumer,
                    random,
                    mutable,
                    config,
                    blockPos,
                    0,
                    j,
                    0
            );
        }
        this.setLog(
                world,
                biConsumer,
                random,
                mutable,
                config,
                blockPos,
                i,
                j,
                k
        );
    }


    @Redirect(
            method = "generate(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                    "Lnet/minecraft/util/math/random/Random;ILnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/trunk/GiantTrunkPlacer;setLog(" +
                            "Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                            "Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos$Mutable;" +
                            "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;" +
                            "Lnet/minecraft/util/math/BlockPos;III)V",
                    ordinal = 0
            )
    )
    private void cf$dontSetLog(GiantTrunkPlacer placer, TestableWorld world, BiConsumer<BlockPos, BlockState> replace,
                               Random random, BlockPos.Mutable m, TreeFeatureConfig config,
                               BlockPos blockPos, int i, int j, int k) {
        if (!CFSettings.giantTreesHaveExtraLogFix) {
            this.setLog(world, replace, random, m, config, blockPos, i, j, k);
        }
    }
}

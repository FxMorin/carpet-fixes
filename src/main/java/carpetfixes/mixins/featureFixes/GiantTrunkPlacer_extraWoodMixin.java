package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(GiantTrunkPlacer.class)
public abstract class GiantTrunkPlacer_extraWoodMixin {


    @Redirect(
            method = "generate(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                    "Ljava/util/Random;ILnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/trunk/GiantTrunkPlacer;" +
                            "setLog(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                            "Ljava/util/Random;Lnet/minecraft/util/math/BlockPos$Mutable;" +
                            "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;" +
                            "Lnet/minecraft/util/math/BlockPos;III)V",
                    ordinal = 1
            )
    )
    private void addSetLog(TestableWorld world, BiConsumer<BlockPos, BlockState> biConsumer,
                           Random random, BlockPos.Mutable mutable, TreeFeatureConfig config,
                           BlockPos blockPos, int i, int j, int k) {
        if (CFSettings.giantTreesHaveExtraLogFix)
            GiantTrunkPlacer.setLog(world, biConsumer, random, mutable, config, blockPos, 0, j, 0);
        GiantTrunkPlacer.setLog(world, biConsumer, random, mutable, config, blockPos, i, j, k);
    }


    @Redirect(
            method = "generate(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Ljava/util/Random;" +
                    "ILnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)" +
                    "Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/trunk/GiantTrunkPlacer;" +
                            "setLog(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                            "Ljava/util/Random;Lnet/minecraft/util/math/BlockPos$Mutable;" +
                            "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;" +
                            "Lnet/minecraft/util/math/BlockPos;III)V",
                    ordinal = 0
            )
    )
    private void dontSetLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replace,
                            Random rand, BlockPos.Mutable mut, TreeFeatureConfig config,
                            BlockPos blockPos, int i, int j, int k) {
        if (!CFSettings.giantTreesHaveExtraLogFix)
            GiantTrunkPlacer.setLog(world, replace, rand, mut, config, blockPos, i, j, k);
    }
}

package carpetfixes.mixins.blockUpdates.TreeDirtMissingUpdates;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.random.AbstractRandom;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.BiConsumer;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.GT_22w13a))
@Mixin(TrunkPlacer.class)
public class TrunkPlacer_logMixin {


    @Inject(
            method = "setToDirt(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;" +
                    "Lnet/minecraft/world/gen/random/AbstractRandom;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)V",
            at = @At("HEAD")
    )
    private static void setToDirtNotTrunk(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                          AbstractRandom random, BlockPos pos, TreeFeatureConfig config,
                                          CallbackInfo ci) {
        CFSettings.LAST_DIRT.get().add(pos);
    }
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w14a))
@Mixin(TrunkPlacer.class)
class TrunkPlacer_oldLogMixin {


    @SuppressWarnings("all")
    @Inject(
            method = "setToDirt(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Ljava/util/Random;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)V",
            at = @At("HEAD")
    )
    private static void setToDirtNotTrunk(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                          Random random, BlockPos pos, TreeFeatureConfig config, CallbackInfo ci) {
        CFSettings.LAST_DIRT.get().add(pos);
    }
}

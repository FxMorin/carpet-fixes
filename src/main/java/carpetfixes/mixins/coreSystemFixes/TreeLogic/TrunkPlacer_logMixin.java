package carpetfixes.mixins.coreSystemFixes.TreeLogic;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class TrunkPlacer_logMixin {


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

package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.random.AbstractRandom;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.GT_22w13a))
@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlock_inconsistentMixin {

    @Shadow
    @Final
    public static BooleanProperty LIT;

    @Shadow
    private static boolean isBurnedOut(World world, BlockPos pos, boolean addNew) {
        return true;
    }


    @Inject(
            method = "scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/random/AbstractRandom;)V",
            cancellable = true,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0
            )
    )
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, AbstractRandom r, CallbackInfo ci) {
        if (CFSettings.inconsistentRedstoneTorchFix) {
            if (isBurnedOut(world, pos, true)) {
                world.syncWorldEvent(1502, pos, 0);
                world.createAndScheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 160);
            }
            world.setBlockState(pos, state.with(LIT, false), 3);
            ci.cancel();
        }
    }
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w14a))
@Mixin(RedstoneTorchBlock.class)
class RedstoneTorchBlock_oldInconsistentMixin {

    @Shadow
    @Final
    public static BooleanProperty LIT;

    @Shadow
    private static boolean isBurnedOut(World world, BlockPos pos, boolean addNew) {
        return true;
    }


    @SuppressWarnings("all")
    @Inject(
            method = "scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
            cancellable = true,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0
            )
    )
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (CFSettings.inconsistentRedstoneTorchFix) {
            if (isBurnedOut(world, pos, true)) {
                world.syncWorldEvent(1502, pos, 0);
                world.createAndScheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 160);
            }
            world.setBlockState(pos, state.with(LIT, false), 3);
            ci.cancel();
        }
    }
}

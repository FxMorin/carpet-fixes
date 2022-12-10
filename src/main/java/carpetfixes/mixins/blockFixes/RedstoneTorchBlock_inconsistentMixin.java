package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes inconsistent redstone torch behavior
 */

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
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random r, CallbackInfo ci) {
        if (CFSettings.inconsistentRedstoneTorchFix) {
            if (isBurnedOut(world, pos, true)) {
                world.syncWorldEvent(1502, pos, 0);
                world.scheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 160);
            }
            world.setBlockState(pos, state.with(LIT, false), 3);
            ci.cancel();
        }
    }
}
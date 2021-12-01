package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlock_inconsistentMixin {

    @Shadow @Final public static BooleanProperty LIT;

    @Shadow private static boolean isBurnedOut(World world, BlockPos pos, boolean addNew) {return true;}


    @Inject(
            method= "scheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
            cancellable = true,
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal=0
            ))
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (CarpetFixesSettings.inconsistentRedstoneTorchFix) {
            if (isBurnedOut(world, pos, true)) {
                world.syncWorldEvent(1502, pos, 0);
                world.createAndScheduleBlockTick(pos, world.getBlockState(pos).getBlock(), 160);
            }
            world.setBlockState(pos, state.with(LIT, false), 3);
            ci.cancel();
        }
    }
}

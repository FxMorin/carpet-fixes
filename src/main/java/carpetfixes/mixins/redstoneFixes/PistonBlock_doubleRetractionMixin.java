package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlock.class)
public abstract class PistonBlock_doubleRetractionMixin {

    /**
     * Double piston retraction is something that used to happen in 1.8.9
     * It's when 2 pistons are depowered in the same tick, allowing both to be pulled together.
     */


    @Inject(
            method = "tryMove",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    ordinal = 1,
                    target = "Lnet/minecraft/world/World;addSyncedBlockEvent(Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/Block;II)V"
            )
    )
    private void onTryMove(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CFSettings.doubleRetraction) world.setBlockState(pos, state.with(PistonBlock.EXTENDED, false), 2);
    }
}

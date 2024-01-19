package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Thanks to Carpet-TIS-Addition for the clean fix
 * Link: https://github.com/TISUnion/Carpet-TIS-Addition
 *
 * Fixes a rail duplication exploit
 */

@Mixin(AbstractRailBlock.class)
public class AbstractRailBlock_duplicationMixin {


    @Inject(
            method = "neighborUpdate",
            require = 1,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/AbstractRailBlock;updateBlockState(" +
                            "Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"
            ),
            cancellable = true
    )
    private void cf$ifRailIsSetCorrectly(BlockState state, World world, BlockPos pos, Block block,
                                         BlockPos neighborPos, boolean moved, CallbackInfo ci) {
        if (CFSettings.railDuplicationFix && !(world.getBlockState(pos).getBlock() instanceof AbstractRailBlock)) {
            ci.cancel();
        }
    }
}

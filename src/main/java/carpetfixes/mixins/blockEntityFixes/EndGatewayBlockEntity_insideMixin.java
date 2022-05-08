package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntity_insideMixin {


    @Inject(
            method = "findExitPortalPos",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void skipAfterBedrock(BlockView world, BlockPos pos, int searchRadius,
                                         boolean force, CallbackInfoReturnable<BlockPos> cir) {
        if (!CFSettings.spawnInsideGatewayFix) return;
        BlockPos pos1 = null;
        for(int i = -searchRadius; i <= searchRadius; ++i) {
            for(int j = -searchRadius; j <= searchRadius; ++j) {
                if (i == 0 && j == 0 && !force) continue;
                for(int k = world.getTopY() - 1; k > (pos1 == null ? world.getBottomY() : pos1.getY()); --k) {
                    BlockPos pos2 = new BlockPos(pos.getX() + i, k, pos.getZ() + j);
                    BlockState state = world.getBlockState(pos2);
                    if (state.isOf(Blocks.BEDROCK)) {
                        if (force) pos1 = pos2;
                        break;
                    }
                    if (state.isFullCube(world, pos2)) {
                        pos1 = pos2;
                        break;
                    }
                }
            }
        }
        cir.setReturnValue(pos1 == null ? pos : pos1);
    }
}

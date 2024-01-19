package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fix pistons being able to push waterlogged blocks if it's a 2-tick pulse
 */
@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_waterloggedMixin {


    @Redirect(
            method = "finish",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;postProcessState(Lnet/minecraft/block/BlockState;" +
                            "Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState cf$removeWaterlogged(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState newState = Block.postProcessState(state, world, pos);
        if (CFSettings.pistonsPushWaterloggedBlocksFix &&
                newState.contains(Properties.WATERLOGGED) && newState.get(Properties.WATERLOGGED)) {
            newState = newState.with(Properties.WATERLOGGED, false);
        }
        return newState;
    }
}

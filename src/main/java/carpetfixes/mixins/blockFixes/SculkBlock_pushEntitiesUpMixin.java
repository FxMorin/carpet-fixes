package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SculkBlock.class)
public class SculkBlock_pushEntitiesUpMixin {


    @Redirect(
            method = "spread",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/BlockState;I)Z"
            )
    )
    private boolean moveEntitiesUp(WorldAccess world, BlockPos pos, BlockState state, int flags) {
        if (CFSettings.entitiesFallThroughSculkFix)
            Block.pushEntitiesUpBeforeBlockChange(world.getBlockState(pos), state, (World)world, pos);
        return world.setBlockState(pos,state, flags);
    }
}

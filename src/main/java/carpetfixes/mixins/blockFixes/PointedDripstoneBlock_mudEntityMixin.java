package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlock_mudEntityMixin {


    @Redirect(
            method = "dripTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/util/math/BlockPos;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            )
    )
    private static boolean moveEntitiesUp(ServerWorld world, BlockPos pos, BlockState state) {
        if (CFSettings.entitiesFallThroughClayFromMudFix)
            Block.pushEntitiesUpBeforeBlockChange(world.getBlockState(pos), state, world, pos);
        return world.setBlockState(pos,state);
    }
}

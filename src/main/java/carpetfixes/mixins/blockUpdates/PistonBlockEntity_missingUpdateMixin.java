package carpetfixes.mixins.blockUpdates;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_missingUpdateMixin {


    /*@Inject(
            method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/PistonBlockEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    shift = At.Shift.AFTER,
                    ordinal = 1
            )
    )
    private static void UpdateTheDamnBlock(World world, BlockPos pos, BlockState state, PistonBlockEntity blockEntity, CallbackInfo ci) {
        BlockPos neighborPos = pos.offset(blockEntity.getFacing());
        //world.updateNeighborsExcept(neighborPos, state.getBlock(), blockEntity.getFacing().getOpposite());
        //world.updateListeners(neighborPos,state, Blocks.AIR.getDefaultState(),3);
        //world.removeBlock(neighborPos,false);
    }*/
}

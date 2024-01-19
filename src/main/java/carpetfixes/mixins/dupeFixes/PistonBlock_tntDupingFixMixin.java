package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

/**
 * Implementation by Carpet-TIS-Addition
 * Thank you very much, the fact that this supports MBE/MTE is also very nice ;)
 * Link: https://github.com/TISUnion/Carpet-TIS-Addition
 *
 * Set all blocks to be moved to air without any kind of update first (yeeted block updaters like dead coral),
 * then let vanilla codes to set the air blocks into b36
 * Before setting a block to air, store the block state right before setting it to air to make sure no block desync
 * will happen (yeeted onRemoved block updater like lit observer).
 */


@Mixin(PistonBlock.class)
public abstract class PistonBlock_tntDupingFixMixin {


    @SuppressWarnings("all")
    @Inject(
        method = "move",
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/BlockState;hasBlockEntity()Z"
            )
        ),
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;size()I",
            shift = At.Shift.AFTER,  // To make sure this will be injected after onMove in movableBEMixin in carpet
            ordinal = 0
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void cf$setAllToBeMovedBlockToAirFirst(World world, BlockPos pos, Direction dir, boolean retract,
                                                   CallbackInfoReturnable<Boolean> cir, BlockPos blockPos,
                                                   PistonHandler pistonHandler, Map<BlockPos, BlockState> map,
                                                   List<BlockPos> list, List<BlockState> list2, List<BlockPos> list3,
                                                   BlockState blockStates[], Direction direction, int j,
                                                   @Share("isFixed") LocalBooleanRef isFixedRef) {
        // just in case the rule gets changed halfway
        isFixedRef.set(CFSettings.pistonDupingFix);

        if (isFixedRef.get()) {
            // vanilla iterating order
            for (int l = list.size() - 1; l >= 0; --l) {
                BlockPos toBeMovedBlockPos = list.get(l);
                // Get the current state to make sure it is the state we want
                BlockState toBeMovedBlockState = world.getBlockState(toBeMovedBlockPos);
                // 68 is vanilla flag, 68 = 4 | 64
                // Added 16 to the vanilla flag, resulting in no block update or state update
                // Added 2 to the vanilla flag, so at those pos that will be air the listeners can be updated correctly
                // Although this cannot yeet onRemoved updaters, but it can prevent attached blocks from breaking,
                // which is nicer than just let them break imo
                world.setBlockState(
                        toBeMovedBlockPos,
                        Blocks.AIR.getDefaultState(),
                        Block.NOTIFY_LISTENERS | Block.NO_REDRAW | Block.FORCE_STATE | Block.MOVED
                );
                // Update containers which contain the old state
                list2.set(l, toBeMovedBlockState);
                // map stores block pos and block state of moved blocks which changed into air due to block being moved
                map.put(toBeMovedBlockPos, toBeMovedBlockState);
            }
        }
    }

    /**
     * Just to make sure blockStates array contains the correct values
     * But ..., when reading states from it, mojang itself inverts the order and reads the wrong state
     * relative to the blockpos
     * When assigning:
     *   blockStates = (list concat with list3 in order).map(world::getBlockState)
     * When reading:
     *   match list3[list3.size()-1] with blockStates[0]
     *   match list3[list3.size()-2] with blockStates[1]
     *   ...
     * The block pos matches wrongly with block state, so mojang uses the wrong block as the source block to
     * emit block updates :thonk:
     * EDITED in 1.16.4: mojang has fixed it now
     *
     * Whatever, just make it behave like vanilla
     */
    @SuppressWarnings("all")
    @Inject(
        method = "move",
        slice = @Slice(
            from = @At(
                value = "FIELD",
                target = "Lnet/minecraft/block/PistonBlock;sticky:Z"
            )
        ),
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;keySet()Ljava/util/Set;",
            ordinal = 0
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void cf$makeSureStatesInBlockStatesIsCorrect(World world, BlockPos pos, Direction dir, boolean retract,
                                                         CallbackInfoReturnable<Boolean> cir, BlockPos blockPos,
                                                         PistonHandler pistonHandler, Map<BlockPos, BlockState> map,
                                                         List<BlockPos> list, List<BlockState> list2,
                                                         List<BlockPos> list3, BlockState[] blockStates,
                                                         BlockState blockState6,
                                                         @Share("isFixed") LocalBooleanRef isFixedRef) {
        if (isFixedRef.get()) {
            // since blockState8 = world.getBlockState(blockPos4) always return AIR due to the changes above
            // some states value in blockStates array need to be corrected
            // list and list2 has the same size and indicating the same block
            int j2 = list3.size();
            for (int l2 = list.size() - 1; l2 >= 0; --l2) {
                blockStates[j2++] = list2.get(l2);
            }
        }
    }
}

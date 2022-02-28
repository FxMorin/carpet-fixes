package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import com.google.common.base.MoreObjects;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.block.TripwireHookBlock.*;


@Mixin(TripwireHookBlock.class)
public abstract class TripwireHookBlock_hookDupeMixin extends Block {

    public TripwireHookBlock_hookDupeMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    protected abstract void playSound(World w, BlockPos pos, boolean a, boolean on, boolean detached, boolean off);

    @Shadow
    protected abstract void updateNeighborsOnAxis(World world, BlockPos pos, Direction direction);


    /**
     * @author FX - PR0CESS
     * @reason Fix for the tripwire hook duplication bug
     */
    @Overwrite
    public void update(World world, BlockPos pos, BlockState state, boolean beingRemoved,
                       boolean bl, int i, @Nullable BlockState blockState) {
        Direction direction = state.get(FACING);
        boolean attached = state.get(ATTACHED);
        boolean powered = state.get(POWERED);
        boolean notRemoving = !beingRemoved;
        boolean on = false;
        int index = 0;
        BlockState[] blockStates = new BlockState[42];
        BlockPos blockPos;
        for(int k = 1; k < 42; ++k) {
            blockPos = pos.offset(direction, k);
            BlockState blockState2 = world.getBlockState(blockPos);
            if (blockState2.isOf(Blocks.TRIPWIRE_HOOK)) {
                if (blockState2.get(FACING) == direction.getOpposite()) index = k;
                break;
            }
            if (!blockState2.isOf(Blocks.TRIPWIRE) && k != i) {
                blockStates[k] = null;
                notRemoving = false;
            } else {
                if (k == i) blockState2 = MoreObjects.firstNonNull(blockState, blockState2);
                boolean armed = !(Boolean)blockState2.get(TripwireBlock.DISARMED);
                on |= armed && blockState2.get(TripwireBlock.POWERED);
                blockStates[k] = blockState2;
                if (k == i) {
                    world.createAndScheduleBlockTick(pos, this, 10);
                    notRemoving &= armed;
                }
            }
        }
        notRemoving &= index > 1;
        on &= notRemoving;
        BlockState newState = this.getDefaultState().with(ATTACHED, notRemoving).with(POWERED, on);
        if (index > 0) {
            blockPos = pos.offset(direction, index);
            Direction blockState2 = direction.getOpposite();
            world.setBlockState(blockPos, newState.with(FACING, blockState2), Block.NOTIFY_ALL);
            this.updateNeighborsOnAxis(world, blockPos, blockState2);
            this.playSound(world, blockPos, notRemoving, on, attached, powered);
        }
        this.playSound(world, pos, notRemoving, on, attached, powered);
        if (!beingRemoved && (!CFSettings.tripwireHookDupeFix || world.getBlockState(pos).isOf(this))) {
            world.setBlockState(pos, newState.with(FACING, direction), Block.NOTIFY_ALL);
            if (bl) this.updateNeighborsOnAxis(world, pos, direction);
        }
        if (attached != notRemoving) {
            for(int x = 1; x < index; ++x) {
                BlockPos pos2 = pos.offset(direction, x);
                BlockState state2 = blockStates[x];
                if (state2 != null) {
                    if (!CFSettings.tripwireNotDisarmingFix || world.getBlockState(pos2).isOf(this)) {
                        world.setBlockState(pos2, state2.with(ATTACHED, notRemoving), Block.NOTIFY_ALL);
                    }
                }
            }
        }
    }
}

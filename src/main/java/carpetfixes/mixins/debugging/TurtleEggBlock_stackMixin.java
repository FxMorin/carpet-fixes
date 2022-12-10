package carpetfixes.mixins.debugging;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A debug tool to determine the complexity of the block update stack.
 * To get the current depth, put turtle effs on a barrier block and give an update to the turtle eggs.
 * To get a stack trace in the logs, place the turtle eggs on a jigsaw block and give them an update.
 */

@Mixin(TurtleEggBlock.class)
public abstract class TurtleEggBlock_stackMixin extends Block {

    public TurtleEggBlock_stackMixin(Settings settings) {
        super(settings);
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (CFSettings.debugStackDepth && world.getBlockState(pos.down()).isOf(Blocks.BARRIER)) {
            long depth = StackWalker.getInstance().walk(Stream::count);
            for (PlayerEntity player : world.getPlayers()) {
                player.sendMessage(Text.of("Stack Depth: "+depth),false);
            }
        }
        if (CFSettings.debugStackTrace && world.getBlockState(pos.down()).isOf(Blocks.JIGSAW)) {
            Arrays.asList(Thread.currentThread().getStackTrace()).forEach(System.out::println);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}

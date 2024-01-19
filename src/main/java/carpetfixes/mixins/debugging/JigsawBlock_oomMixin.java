package carpetfixes.mixins.debugging;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool that allows you to execute an OOM exception on demand. All you need to do is put a lightning rod on a
 * jigsaw block and then give the jigsaw block a block update.
 */

@Mixin(JigsawBlock.class)
public class JigsawBlock_oomMixin extends Block {
    public JigsawBlock_oomMixin(Settings settings) {
        super(settings);
    }

    @Unique
    private void cf$simulateOOM() {
        List<byte[]> OutOfMemoryList = new ArrayList<>(); // Filled to cause an OutOfMemory Error
        try {
            while (true) OutOfMemoryList.add(new byte[10 * 1024 * 1024]); // Use 10MB of memory
        } catch (OutOfMemoryError err) {
            OutOfMemoryList.clear();
            throw err;
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean n) {
        if (CFSettings.debugSimulatedOutOfMemory && !fromPos.equals(pos.up()) &&
                world.getBlockState(pos.up()).isOf(Blocks.LIGHTNING_ROD)) {
            cf$simulateOOM();
        }
    }
}

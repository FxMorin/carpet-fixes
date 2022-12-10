package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static net.minecraft.block.NoteBlock.POWERED;

/**
 * Fixes noteblocks placed in a spot where they should be powered, resulting in them not being powered. Budded
 */

@Mixin(NoteBlock.class)
public abstract class NoteBlock_placedMixin extends Block {

    public NoteBlock_placedMixin(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (CFSettings.noteBlockNotPoweredOnPlaceFix) {
            boolean bl = world.isReceivingRedstonePower(pos);
            if (bl != state.get(POWERED)) {
                world.setBlockState(pos, state.with(POWERED, bl), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
            }
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }
}

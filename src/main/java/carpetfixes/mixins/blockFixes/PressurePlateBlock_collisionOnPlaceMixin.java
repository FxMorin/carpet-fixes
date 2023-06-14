package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * Fixes projectiles not being detected when placing a pressure plate
 */

@Mixin(PressurePlateBlock.class)
public abstract class PressurePlateBlock_collisionOnPlaceMixin extends AbstractPressurePlateBlock {

    @Shadow
    @Final
    public static BooleanProperty POWERED;

    public PressurePlateBlock_collisionOnPlaceMixin(Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (CFSettings.projectileNotDetectedOnPlaceFix && !oldState.isOf(state.getBlock()))
            this.tryPowerOnPlace(state,world,pos);
    }

    private void tryPowerOnPlace(BlockState state, World world, BlockPos pos) {
        List<? extends Entity> list = world.getNonSpectatingEntities(
                Entity.class,
                state.getOutlineShape(world, pos).getBoundingBox().offset(pos)
        );
        boolean bl = !list.isEmpty();
        boolean bl2 = state.get(POWERED);
        if (bl != bl2) {
            world.setBlockState(pos, state.with(POWERED, bl), 3);
            this.updateNeighbors(world, pos);
            if (bl) {
                world.playSound(null, pos, this.blockSetType.pressurePlateClickOff(), SoundCategory.BLOCKS);
                world.emitGameEvent(list.stream().findFirst().orElse(null), GameEvent.BLOCK_DEACTIVATE, pos);
            } else {
                world.playSound(null, pos, this.blockSetType.pressurePlateClickOn(), SoundCategory.BLOCKS);
                world.emitGameEvent(list.stream().findFirst().orElse(null), GameEvent.BLOCK_ACTIVATE, pos);
            }
        }
        if (bl) world.scheduleBlockTick(new BlockPos(pos), this, this.getTickRate());
    }
}

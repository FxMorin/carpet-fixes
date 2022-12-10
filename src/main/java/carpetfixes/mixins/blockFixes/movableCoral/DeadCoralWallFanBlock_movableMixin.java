package carpetfixes.mixins.blockFixes.movableCoral;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Fixes corals being movable
 */
@Mixin(DeadCoralWallFanBlock.class)
public class DeadCoralWallFanBlock_movableMixin extends Block {

    public DeadCoralWallFanBlock_movableMixin(Settings settings) {
        super(settings);
    }


    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return CFSettings.movableCoralFanFix ? PistonBehavior.DESTROY : super.getPistonBehavior(state);
    }
}

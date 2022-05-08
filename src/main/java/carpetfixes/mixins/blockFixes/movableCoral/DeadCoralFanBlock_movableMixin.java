package carpetfixes.mixins.blockFixes.movableCoral;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DeadCoralFanBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DeadCoralFanBlock.class)
public class DeadCoralFanBlock_movableMixin extends Block {

    public DeadCoralFanBlock_movableMixin(Settings settings) {
        super(settings);
    }


    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        if (CFSettings.movableCoralFanFix) return PistonBehavior.DESTROY;
        return super.getPistonBehavior(state);
    }
}

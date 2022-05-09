package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GrindstoneBlock.class)
public class GrindstoneBlock_movableMixin extends Block {
    public GrindstoneBlock_movableMixin(Settings settings) {
        super(settings);
    }


    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        if (CFSettings.grindstonesNotMovableFix) return PistonBehavior.NORMAL;
        return super.getPistonBehavior(state);
    }
}

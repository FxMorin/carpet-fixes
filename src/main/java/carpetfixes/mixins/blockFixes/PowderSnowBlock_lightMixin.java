package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlock_lightMixin extends Block {

    public PowderSnowBlock_lightMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos p) {
        return CarpetFixesSettings.powderedSnowOpacityFix ? world.getMaxLightLevel() : super.getOpacity(state,world,p);
    }
}

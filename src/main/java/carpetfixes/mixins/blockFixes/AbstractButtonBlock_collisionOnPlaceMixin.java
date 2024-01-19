package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Fixes buttons not being activated when placed on a projectile
 */
@Mixin(ButtonBlock.class)
public abstract class AbstractButtonBlock_collisionOnPlaceMixin extends AbstractBlock {

    public AbstractButtonBlock_collisionOnPlaceMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    protected abstract void tryPowerWithProjectiles(BlockState state, World world, BlockPos pos);


    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (CFSettings.projectileNotDetectedOnPlaceFix && !oldState.isOf(state.getBlock())) {
            this.tryPowerWithProjectiles(state, world, pos);
        }
    }
}

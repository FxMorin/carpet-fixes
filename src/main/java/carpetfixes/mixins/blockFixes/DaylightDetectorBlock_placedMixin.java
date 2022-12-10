package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static net.minecraft.block.DaylightDetectorBlock.INVERTED;
import static net.minecraft.block.DaylightDetectorBlock.POWER;

/**
 * Fixes daylight detector nor being in the correct state when being placed down
 */
@Mixin(DaylightDetectorBlock.class)
public abstract class DaylightDetectorBlock_placedMixin extends Block {

    public DaylightDetectorBlock_placedMixin(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (CFSettings.daylightSensorPlacementFix) {
            // this.updateState(state, world, pos); except it needs to be a forced place
            int i = world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness();
            if (state.get(INVERTED)) {
                i = 15 - i;
            } else if (i > 0) {
                float f = world.getSkyAngleRadians(1.0F);
                f += ((f < (float) Math.PI ? 0.0F : (float) (Math.PI * 2)) - f) * 0.2F;
                i = Math.round((float)i * MathHelper.cos(f));
            }
            i = MathHelper.clamp(i, 0, 15);
            if (state.get(POWER) != i)
                world.setBlockState(pos, state.with(POWER, i), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }
}

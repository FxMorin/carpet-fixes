package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(Block.class)
public abstract class Block_itemPositionMixin extends AbstractBlock {

    @Shadow private static void dropStack(World world, Supplier<ItemEntity> itemEntitySupplier, ItemStack stack){}

    public Block_itemPositionMixin(Settings settings) {super(settings);}


    /**
     * @author FX - PR0CESS
     * @reason Fixes floating point errors
     */
    @Overwrite
    public static void dropStack(World world, BlockPos pos, ItemStack stack) {
        if (CarpetFixesSettings.tileDropsAffectedByFloatingPointFix) {
            double f = EntityType.ITEM.getHeight() / 2.0D;
            double d = ((double) pos.getX() + 0.5D) + MathHelper.nextDouble(world.random, -0.25D, 0.25D);
            double e = ((double) pos.getY() + 0.5D) + MathHelper.nextDouble(world.random, -0.25D, 0.25D) - f;
            double g = ((double) pos.getZ() + 0.5D) + MathHelper.nextDouble(world.random, -0.25D, 0.25D);
            dropStack(world, () -> new ItemEntity(world, d, e, g, stack), stack);
        } else {
            float f = EntityType.ITEM.getHeight() / 2.0F;
            double d = (double) ((float) pos.getX() + 0.5F) + MathHelper.nextDouble(world.random, -0.25D, 0.25D);
            double e = (double) ((float) pos.getY() + 0.5F) + MathHelper.nextDouble(world.random, -0.25D, 0.25D) - (double) f;
            double g = (double) ((float) pos.getZ() + 0.5F) + MathHelper.nextDouble(world.random, -0.25D, 0.25D);
            dropStack(world, () -> new ItemEntity(world, d, e, g, stack), stack);
        }
    }

    /**
     * @author FX - PR0CESS
     * @reason Fixes floating point errors
     */
    @Overwrite
    public static void dropStack(World world, BlockPos pos, Direction direction, ItemStack stack) {
        if (CarpetFixesSettings.tileDropsAffectedByFloatingPointFix) {
            int i = direction.getOffsetX();
            int j = direction.getOffsetY();
            int k = direction.getOffsetZ();
            double f = EntityType.ITEM.getWidth() / 2.0D;
            double g = EntityType.ITEM.getHeight() / 2.0D;
            double d = ((double) pos.getX() + 0.5D) + (i == 0 ? MathHelper.nextDouble(world.random, -0.25D, 0.25D) : ((double) i * (0.5F + f)));
            double e = ((double) pos.getY() + 0.5D) + (j == 0 ? MathHelper.nextDouble(world.random, -0.25D, 0.25D) : ((double) j * (0.5F + g))) - g;
            double h = ((double) pos.getZ() + 0.5D) + (k == 0 ? MathHelper.nextDouble(world.random, -0.25D, 0.25D) : ((double) k * (0.5F + f)));
            createItemEntity(world, stack, i, j, k, d, e, h);
        } else {
            int i = direction.getOffsetX();
            int j = direction.getOffsetY();
            int k = direction.getOffsetZ();
            float f = EntityType.ITEM.getWidth() / 2.0F;
            float g = EntityType.ITEM.getHeight() / 2.0F;
            double d = (double)((float)pos.getX() + 0.5F) + (i == 0 ? MathHelper.nextDouble(world.random, -0.25D, 0.25D) : (double)((float)i * (0.5F + f)));
            double e = (double)((float)pos.getY() + 0.5F) + (j == 0 ? MathHelper.nextDouble(world.random, -0.25D, 0.25D) : (double)((float)j * (0.5F + g))) - (double)g;
            double h = (double)((float)pos.getZ() + 0.5F) + (k == 0 ? MathHelper.nextDouble(world.random, -0.25D, 0.25D) : (double)((float)k * (0.5F + f)));
            createItemEntity(world, stack, i, j, k, d, e, h);
        }
    }

    private static void createItemEntity(World world, ItemStack stack, int i, int j, int k, double d, double e, double h) {
        double l = i == 0 ? MathHelper.nextDouble(world.random, -0.1D, 0.1D) : (double) i * 0.1D;
        double m = j == 0 ? MathHelper.nextDouble(world.random, 0.0D, 0.1D) : (double) j * 0.1D + 0.1D;
        double n = k == 0 ? MathHelper.nextDouble(world.random, -0.1D, 0.1D) : (double) k * 0.1D;
        dropStack(world, () -> new ItemEntity(world, d, e, h, stack, l, m, n), stack);
    }
}

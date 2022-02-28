package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(Block.class)
public class Block_itemPositionMixin {

    @Shadow
    private static void dropStack(World world, Supplier<ItemEntity> itemEntitySupplier, ItemStack stack) {}


    @Inject(
            method = "dropStack(Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void dropStack(World world, BlockPos pos, ItemStack stack, CallbackInfo ci) {
        if (CFSettings.tileDropsAffectedByFloatingPointFix) {
            double f = EntityType.ITEM.getHeight() / 2.0D;
            double d = ((double) pos.getX() + 0.5D) + MathHelper.nextDouble(world.random, -0.25D, 0.25D);
            double e = ((double) pos.getY() + 0.5D) + MathHelper.nextDouble(world.random, -0.25D, 0.25D) - f;
            double g = ((double) pos.getZ() + 0.5D) + MathHelper.nextDouble(world.random, -0.25D, 0.25D);
            dropStack(world, () -> new ItemEntity(world, d, e, g, stack), stack);
            ci.cancel();
        }
    }


    @Inject(
            method = "dropStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/util/math/Direction;Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void dropStack(World world, BlockPos pos, Direction direction, ItemStack stack, CallbackInfo ci) {
        if (CFSettings.tileDropsAffectedByFloatingPointFix) {
            int i = direction.getOffsetX(), j = direction.getOffsetY(), k = direction.getOffsetZ();
            double f = EntityType.ITEM.getWidth() / 2.0D, g = EntityType.ITEM.getHeight() / 2.0D;
            double x = ((double) pos.getX() + 0.5D) + (i == 0 ?
                    MathHelper.nextDouble(world.random, -0.25D, 0.25D) :
                    ((double) i * (0.5F + f))
            );
            double y = ((double) pos.getY() + 0.5D) + (j == 0 ?
                    MathHelper.nextDouble(world.random, -0.25D, 0.25D) :
                    ((double) j * (0.5F + g))
            ) - g;
            double z = ((double) pos.getZ() + 0.5D) + (k == 0 ?
                    MathHelper.nextDouble(world.random, -0.25D, 0.25D) :
                    ((double) k * (0.5F + f))
            );
            createItemEntity(world, stack, i, j, k, x, y, z);
            ci.cancel();
        }
    }

    private static void createItemEntity(World world, ItemStack stack,
                                         int i, int j, int k, double d, double e, double h) {
        double l = i == 0 ? MathHelper.nextDouble(world.random, -0.1D, 0.1D) : (double) i * 0.1D;
        double m = j == 0 ? MathHelper.nextDouble(world.random, 0.0D, 0.1D) : (double) j * 0.1D + 0.1D;
        double n = k == 0 ? MathHelper.nextDouble(world.random, -0.1D, 0.1D) : (double) k * 0.1D;
        dropStack(world, () -> new ItemEntity(world, d, e, h, stack, l, m, n), stack);
    }
}

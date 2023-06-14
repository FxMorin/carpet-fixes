package carpetfixes.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Consumer;

public class CenterUtils {

    private static final double VELOCITY_AFFECTING_POS_Y_OFFSET = 0.5000001;
    private static final double OFFSET = 0.0001;

    public static void iterateTouchingBlocks(Entity entity, Consumer<BlockPos.Mutable> perBlock) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = BlockPos.ofFloored(box.minX + OFFSET, entity.getBlockY(), box.minZ + OFFSET);
        BlockPos blockPos2 = BlockPos.ofFloored(box.maxX - OFFSET, entity.getBlockY(), box.maxZ - OFFSET);
        if (entity.getWorld().isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i)
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k)
                    perBlock.accept(mutable.set(i, blockPos.getY(), k));
        }
    }

    public static void checkFallCollision(Entity entity, float fallDistance) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = BlockPos.ofFloored(box.minX + OFFSET, entity.getBlockY(), box.minZ + OFFSET);
        BlockPos blockPos2 = BlockPos.ofFloored(box.maxX - OFFSET, entity.getBlockY(), box.maxZ - OFFSET);
        if (entity.getWorld().isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            boolean createdEvent = false;
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    BlockState state = entity.getWorld().getBlockState(mutable);
                    state.getBlock().onLandedUpon(entity.getWorld(), state, mutable, entity, fallDistance);
                    if (!createdEvent && !state.isAir() && !state.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                        entity.emitGameEvent(GameEvent.HIT_GROUND);
                        createdEvent = true;
                    }
                }
            }
        }
    }

    public static float checkJumpVelocityOnCollision(Entity entity, World world) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = BlockPos.ofFloored(box.minX + OFFSET, entity.getBlockY(), box.minZ + OFFSET);
        BlockPos blockPos2 = BlockPos.ofFloored(box.maxX - OFFSET, entity.getBlockY(), box.maxZ - OFFSET);
        if (world.isRegionLoaded(blockPos, blockPos2)) {
            double newY = box.minY - VELOCITY_AFFECTING_POS_Y_OFFSET;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            float fastestBlock = 1.0F; //Highest value
            float slowestBlock = 1.0F; //Smallest value
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    float topBlock = world.getBlockState(mutable).getBlock().getJumpVelocityMultiplier();
                    if ((double)topBlock == 1.0D) {
                        mutable.set(i, newY, k);
                        float affectingBlock = world.getBlockState(mutable).getBlock().getJumpVelocityMultiplier();
                        slowestBlock = Math.min(affectingBlock, slowestBlock);
                        fastestBlock = Math.max(affectingBlock, fastestBlock);
                    } else {
                        slowestBlock = Math.min(topBlock, slowestBlock);
                        fastestBlock = Math.max(topBlock, fastestBlock);
                    }
                }
            }
            return (double)slowestBlock < 1.0D ? slowestBlock : fastestBlock;
        }
        return 1.0F;
    }

    public static float checkVelocityOnCollision(Entity entity, World world) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = BlockPos.ofFloored(box.minX + OFFSET, entity.getBlockY(), box.minZ + OFFSET);
        BlockPos blockPos2 = BlockPos.ofFloored(box.maxX - OFFSET, entity.getBlockY(), box.maxZ - OFFSET);
        if (world.isRegionLoaded(blockPos, blockPos2)) {
            double newY = box.minY - VELOCITY_AFFECTING_POS_Y_OFFSET;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            float fastestBlock = 1.0F; //Highest value
            float slowestBlock = 1.0F; //Smallest value
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    BlockState blockState = world.getBlockState(mutable);
                    float topBlock = blockState.getBlock().getVelocityMultiplier();
                    if ((double) topBlock == 1.0D &&
                            !blockState.isOf(Blocks.WATER) && !blockState.isOf(Blocks.BUBBLE_COLUMN)) {
                        mutable.set(i, newY, k);
                        float affectingBlock = world.getBlockState(mutable).getBlock().getVelocityMultiplier();
                        slowestBlock = Math.min(affectingBlock, slowestBlock);
                        fastestBlock = Math.max(affectingBlock, fastestBlock);
                    } else {
                        slowestBlock = Math.min(topBlock, slowestBlock);
                        fastestBlock = Math.max(topBlock, fastestBlock);
                    }
                }
            }
            return (double)slowestBlock < 1.0D ? slowestBlock : fastestBlock;
        }
        return 1.0F;
    }
}

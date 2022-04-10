package carpetfixes.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CenterUtils {

    private static final double VELOCITY_AFFECTING_POS_Y_OFFSET = 0.5;
    private static final double OFFSET = 0.0001;

    //TODO: Change some of these to work with consumers, to minimize duplicate code

    public static void checkStepOnCollision(Entity entity) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = new BlockPos(box.minX + OFFSET, box.minY + OFFSET, box.minZ + OFFSET);
        BlockPos blockPos2 = new BlockPos(box.maxX - OFFSET, box.minY - OFFSET, box.maxZ - OFFSET);
        if (entity.world.isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    BlockState blockState = entity.world.getBlockState(mutable);
                    blockState.getBlock().onSteppedOn(entity.world, mutable, blockState, entity);
                }
            }
        }
    }

    public static void checkEntityLandOnCollision(Entity entity) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = new BlockPos(box.minX + OFFSET, box.minY + OFFSET, box.minZ + OFFSET);
        BlockPos blockPos2 = new BlockPos(box.maxX - OFFSET, box.minY - OFFSET, box.maxZ - OFFSET);
        if (entity.world.isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    BlockState blockState = entity.world.getBlockState(mutable);
                    blockState.getBlock().onEntityLand(entity.world, entity);
                }
            }
        }
    }

    public static void checkFallCollision(Entity entity, float fallDistance) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = new BlockPos(box.minX + OFFSET, box.minY + OFFSET, box.minZ + OFFSET);
        BlockPos blockPos2 = new BlockPos(box.maxX - OFFSET, box.minY - OFFSET, box.maxZ - OFFSET);
        if (entity.world.isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            boolean createdEvent = false;
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    BlockState state = entity.world.getBlockState(mutable);
                    state.getBlock().onLandedUpon(entity.world, state, mutable, entity, fallDistance);
                    if (!createdEvent && !state.isAir() && !state.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                        entity.emitGameEvent(GameEvent.HIT_GROUND);
                        createdEvent = true;
                    }
                }
            }
        }
    }

    public static float checkJumpVelocityOnCollision(Box box, World world) {
        BlockPos blockPos = new BlockPos(box.minX + OFFSET, box.minY + OFFSET, box.minZ + OFFSET);
        BlockPos blockPos2 = new BlockPos(box.maxX - OFFSET, box.minY - OFFSET, box.maxZ - OFFSET);
        if (world.isRegionLoaded(blockPos, blockPos2)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            float fastestBlock = 1.0F; //Highest value
            float slowestBlock = 1.0F; //Smallest value
            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
                for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, blockPos.getY(), k);
                    float topBlock = world.getBlockState(mutable).getBlock().getJumpVelocityMultiplier();
                    if ((double)topBlock == 1.0D) {
                        mutable.set(i, box.minY-VELOCITY_AFFECTING_POS_Y_OFFSET, k);
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

    public static float checkVelocityOnCollision(Box box, World world) {
        BlockPos blockPos = new BlockPos(box.minX + OFFSET, box.minY + OFFSET, box.minZ + OFFSET);
        BlockPos blockPos2 = new BlockPos(box.maxX - OFFSET, box.minY - OFFSET, box.maxZ - OFFSET);
        if (world.isRegionLoaded(blockPos, blockPos2)) {
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
                        mutable.set(i, box.minY - VELOCITY_AFFECTING_POS_Y_OFFSET, k);
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

package carpetfixes.helpers;

import carpetfixes.CarpetFixesServer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.block.NeighborUpdater;
import org.jetbrains.annotations.Nullable;

/**
 * A more memory efficient implementation of the new NeighborUpdater
 * @author Hilligans - https://github.com/Hilligans
 *
 * Instead of wrapping everything in objects, every update converts its data into an array of integers with an id
 * to identify what type of update it is. The header id is the top 3 bits of the first integer stored in the
 * current position in the array.
 *
 * The normal list acts like a stack with the 0th element being the first in the stack, when adding we add to
 * the current pointer in the array and then adjust that pointer accordingly.
 *
 * pointer = 2
 * [data1,data2,empty,empty,empty]
 *
 * pointer = 3
 * [data1,data2,addedData,empty,empty]
 *
 * The pending list works in the opposite way. The first element in the list is at the end of the array.
 * The vanilla implementation uses a normal list and then iterates over the list in reverse to add it to
 * the main stack. It's built up in reverse to avoid having to iterate over the list in reverse.
 * We can instead just do an array copy since its already flipped.
 *
 * queuedUpdates = [data1,data2,empty,empty,empty]
 * pendingUpdates = [empty,empty,empty,data4,data3]
 *
 * add data5 to pending
 *
 * pendingUpdates = [empty,empty,data5,data4,data3]
 *
 * copy data to queuedUpdates
 *
 * queuedUpdates = [data1,data2,data5,data4,data3]
 *
 */

public class MemEfficientNeighborUpdater implements NeighborUpdater {

    private static final int UPDATE_AMT = UPDATE_ORDER.length;

    protected static final int MAX_UPDATE_DATA_SIZE = 9;

    protected int[] queuedUpdates;
    protected int[] pendingUpdates;

    protected int maxSize;
    protected int pointer = 0;
    protected int pendingPointer = 0;
    protected int minSize;
    protected int effectiveSize;
    protected int pendingEffectiveSize;

    private final World world;

    public MemEfficientNeighborUpdater(World world, int maxSize) {
        this(world, maxSize, 16384);
    }

    public MemEfficientNeighborUpdater(World world) {
        this(world, 100000000, 16384);
    }

    public MemEfficientNeighborUpdater(World world, int maxSize, int minSize) {
        this.maxSize = maxSize;
        this.world = world;
        this.minSize = minSize;
        this.effectiveSize = minSize;
        this.pendingEffectiveSize = minSize;
        this.queuedUpdates = new int[MAX_UPDATE_DATA_SIZE * minSize];
        this.pendingUpdates = new int[MAX_UPDATE_DATA_SIZE * minSize];
    }




    @Override // Shape Updates
    public void replaceWithStateForNeighborUpdate(Direction dir, BlockState state,
                                                  BlockPos blockPos, BlockPos sourcePos, int i, int j) {
        enqueue(new int[] {
                dir.getId() | (2 << 29),
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ(),
                sourcePos.getX(),
                sourcePos.getY(),
                sourcePos.getZ(),
                getID(state),
                i
        });
    }


    @Override
    public void updateNeighbor(BlockPos pos, Block sourceBlock, BlockPos sourcePos) {
        enqueue(new int[] {
                createDataType(sourceBlock, 0),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                sourcePos.getX(),
                sourcePos.getY(),
                sourcePos.getZ()
        });
    }


    @Override
    public void updateNeighbor(BlockState state, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        enqueue(new int[] {
                createDataType(sourceBlock, 1),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                sourcePos.getX(),
                sourcePos.getY(),
                sourcePos.getZ(),
                getID(state),
                notify ? 1 : 0
        });
    }

    @Override
    public void updateNeighbors(BlockPos pos, Block sourceBlock, @Nullable Direction except) {
        int id = except == null ? 6 : except.getId();
        enqueue(new int[] {
                createDataType(sourceBlock, 3),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                id,
                0
        });
    }

    public boolean processing;

    public void enqueue(int[] data) {
        if (pointer + pendingPointer + 1 > maxSize) {
            CarpetFixesServer.LOGGER.error("Too many chained neighbor updates. Skipping the rest.");
            return;
        }


        if(pointer == 0 && pendingPointer == 0) {
            System.arraycopy(data, 0, queuedUpdates, 0, data.length);
            pointer++;
        } else {
            try {
                if (pendingPointer + 1 > pendingEffectiveSize) resizePending();
                System.arraycopy(data, 0, pendingUpdates, getPendingPointer(), data.length);
                pendingPointer++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        runLoop();
    }

    public void copy() {
        int newSize = (pointer + pendingPointer) * MAX_UPDATE_DATA_SIZE;
        if(newSize > queuedUpdates.length) resize(newSize);
        System.arraycopy(
                pendingUpdates,
                getPendingPointer() + MAX_UPDATE_DATA_SIZE,
                queuedUpdates,
                getPointer(),
                (pendingPointer) * MAX_UPDATE_DATA_SIZE
        );
        pointer += pendingPointer;
        pendingPointer = 0;
        pendingEffectiveSize = minSize;
        pendingUpdates = new int[minSize * MAX_UPDATE_DATA_SIZE];
    }

    public void runLoop() {
        if (!processing) {
            processing = true;
            while (pointer != 0) {
                processBlock();
                if(pendingPointer != 0) copy();
            }
            if (effectiveSize != minSize) {
                queuedUpdates = new int[minSize * MAX_UPDATE_DATA_SIZE];
                effectiveSize = minSize;
            }
            processing = false;
        }
    }

    private void processBlock() {
        pointer--;
        int header = queuedUpdates[getPointer()];
        int type = getHeader(header);
        if(type == 0) {
            BlockPos pos = getPos(getPointer() + 1);
            NeighborUpdater.tryNeighborUpdate(
                    world,                                     // World
                    world.getBlockState(pos),                  // Block State
                    getPos(getPointer() + 1),                  // Pos
                    fromHeader(header),                        // Source Block
                    getPos(getPointer() + 4),                  // Source Pos
                    false                                      // Notify
            );
        }  else if (type == 1) {
            NeighborUpdater.tryNeighborUpdate(
                    world,                                     // World
                    getState(queuedUpdates[getPointer() + 7]), // State
                    getPos(getPointer() + 1),                  // Pos
                    fromHeader(header),                        // Source Block
                    getPos(getPointer() + 4),                  // Source Pos
                    queuedUpdates[getPointer() + 8] == 1       // Moved By Piston
            );
        } else if(type == 2) { // Shape Update
            NeighborUpdater.replaceWithStateForNeighborUpdate(
                    world,                                     // World
                    Direction.byId(header & 0b111),            // Direction
                    getState(queuedUpdates[getPointer() + 7]), // State
                    getPos(getPointer() + 1),                  // Pos
                    getPos(getPointer() + 4),                  // Neighbor Pos
                    queuedUpdates[getPointer() + 8],           // Flags
                    512                                        // Update Depth Left
            );
        } else if(type == 3) {
            int dir = queuedUpdates[getPointer() + 4];
            int currentDirIndex = queuedUpdates[getPointer() + 5];
            if (dir != 6 && currentDirIndex < UPDATE_AMT && UPDATE_ORDER[currentDirIndex] == Direction.byId(dir)) {
                currentDirIndex++;
            }
            queuedUpdates[getPointer() + 5] = currentDirIndex + 1;
            if (currentDirIndex == UPDATE_AMT) return;
            BlockPos pos = getPos(getPointer() + 1);
            BlockPos blockPos = pos.offset(UPDATE_ORDER[currentDirIndex]);
            if (currentDirIndex != UPDATE_AMT) pointer++;
            world.getBlockState(blockPos).neighborUpdate(
                    world,                                     // World
                    blockPos,                                  // Pos
                    fromHeader(header),                        // Source Block
                    pos,                                       // Source Pos
                    false                                      // Notify
            );
        } else {
            throw new RuntimeException("Unknown update type: " + type + ", something went wrong.");
        }
    }

    private BlockPos getPos(int offset) {
        return new BlockPos(queuedUpdates[offset], queuedUpdates[offset + 1], queuedUpdates[offset + 2]);
    }

    private int getPointer() {
        return pointer * MAX_UPDATE_DATA_SIZE;
    }

    private int getPendingPointer() {
        return (pendingUpdates.length / MAX_UPDATE_DATA_SIZE - pendingPointer - 1) * MAX_UPDATE_DATA_SIZE;
    }

    private void resize(int size) {
        int newSize = Math.min(size, maxSize * MAX_UPDATE_DATA_SIZE);
        if(newSize != queuedUpdates.length) {
            int[] newArr = new int[newSize];
            System.arraycopy(queuedUpdates, 0, newArr, 0, queuedUpdates.length);
            queuedUpdates = newArr;
            effectiveSize = newSize / MAX_UPDATE_DATA_SIZE;
        }
    }

    private void resizePending() {
        int newSize = Math.min(pendingUpdates.length * 2, maxSize * MAX_UPDATE_DATA_SIZE);
        if(newSize != pendingUpdates.length) {
            int[] newArr = new int[newSize];
            System.arraycopy(pendingUpdates, 0, newArr, (newSize - pendingUpdates.length), pendingUpdates.length);
            pendingUpdates = newArr;
            pendingEffectiveSize = newSize / MAX_UPDATE_DATA_SIZE;
        }
    }

    private static int createDataType(Block block, int extra) {
        return getID(block.getDefaultState()) | (extra << 29);
    }

    public static int getID(BlockState blockState) {
        return Block.getRawIdFromState(blockState);
    }

    public static Block fromHeader(int val) {
        return getBlock(val & 0b00011111111111111111111111111111);
    }

    public static int getHeader(int val) {
        return val >> 29;
    }

    public static Block getBlock(int val) {
        return getState(val).getBlock();
    }

    public static BlockState getState(int val) {
        return Block.getStateFromRawId(val);
    }
}

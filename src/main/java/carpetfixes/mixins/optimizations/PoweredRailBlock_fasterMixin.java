package carpetfixes.mixins.optimizations;

import carpet.CarpetSettings;
import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

/**
 * Full Rewrite of the powered rail iteration logic.
 * This rewrite brings a massive performance boost while keeping the vanilla order. This is achieved by running all the
 * powered rail logic from a single rail instead of each block iterating separately. Which was not only very
 * expensive but also completely unnecessary and with a lot of massive overhead
 */

@Mixin(value = PoweredRailBlock.class, priority = 990)
public abstract class PoweredRailBlock_fasterMixin extends AbstractRailBlock {

    @Shadow
    @Final
    public static EnumProperty<RailShape> SHAPE;

    @Shadow
    @Final
    public static BooleanProperty POWERED;

    PoweredRailBlock self = (PoweredRailBlock)(Object)this;

    protected PoweredRailBlock_fasterMixin(boolean allowCurves, Settings settings) {
        super(allowCurves, settings);
    }

    @Shadow
    protected boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean bl, int distance) {
        return false;
    }

    private static final Direction[] EAST_WEST_DIR = new Direction[]{Direction.WEST, Direction.EAST};
    private static final Direction[] NORTH_SOUTH_DIR = new Direction[]{Direction.SOUTH, Direction.NORTH};

    private static final int FORCE_PLACE = MOVED | FORCE_STATE | NOTIFY_LISTENERS;

    protected boolean isPoweredByOtherRailsFaster(World world, BlockPos pos, boolean bl, int distance,
                                                  RailShape shape, HashMap<BlockPos,Boolean> checkedPos) {
        BlockState blockState = world.getBlockState(pos);
        boolean speedCheck = checkedPos.containsKey(pos) && checkedPos.get(pos);
        if (speedCheck) {
            return world.isReceivingRedstonePower(pos) ||
                    this.isPoweredByOtherRailsFaster(world, pos, blockState, bl, distance + 1, checkedPos);
        } else {
            if (blockState.isOf(this)) {
                RailShape railShape = blockState.get(SHAPE);
                if (shape == RailShape.EAST_WEST && (
                        railShape == RailShape.NORTH_SOUTH ||
                        railShape == RailShape.ASCENDING_NORTH ||
                        railShape == RailShape.ASCENDING_SOUTH
                ) || shape == RailShape.NORTH_SOUTH && (
                        railShape == RailShape.EAST_WEST ||
                        railShape == RailShape.ASCENDING_EAST ||
                        railShape == RailShape.ASCENDING_WEST
                )) {
                    return false;
                } else if (blockState.get(POWERED)) {
                    return world.isReceivingRedstonePower(pos) ||
                            this.isPoweredByOtherRailsFaster(world, pos, blockState, bl, distance + 1, checkedPos);
                } else {
                    return false;
                }
            }
            return false;
        }
    }

    protected boolean isPoweredByOtherRailsFaster(World world, BlockPos pos, BlockState state, boolean bl,
                                                  int distance, HashMap<BlockPos,Boolean> checkedPos) {
        if (distance >= CarpetSettings.railPowerLimit-1) return false;
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean bl2 = true;
        RailShape railShape = state.get(SHAPE);
        switch (railShape.ordinal()) {
            case 0 -> {
                if (bl) ++k;
                else --k;
            }
            case 1 -> {
                if (bl) --i;
                else ++i;
            }
            case 2 -> {
                if (bl) {
                    --i;
                } else {
                    ++i;
                    ++j;
                    bl2 = false;
                }
                railShape = RailShape.EAST_WEST;
            }
            case 3 -> {
                if (bl) {
                    --i;
                    ++j;
                    bl2 = false;
                } else {
                    ++i;
                }
                railShape = RailShape.EAST_WEST;
            }
            case 4 -> {
                if (bl) {
                    ++k;
                } else {
                    --k;
                    ++j;
                    bl2 = false;
                }
                railShape = RailShape.NORTH_SOUTH;
            }
            case 5 -> {
                if (bl) {
                    ++k;
                    ++j;
                    bl2 = false;
                } else {
                    --k;
                }
                railShape = RailShape.NORTH_SOUTH;
            }
        }
        return this.isPoweredByOtherRailsFaster(
                world, new BlockPos(i, j, k),
                bl, distance, railShape, checkedPos
        ) ||
                (bl2 && this.isPoweredByOtherRailsFaster(
                        world, new BlockPos(i, j - 1, k),
                        bl, distance, railShape, checkedPos
                ));
    }

    @Inject(
            method = "updateBlockState(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor, CallbackInfo ci) {
        if (CFSettings.optimizedPoweredRails) {
            boolean shouldBePowered = world.isReceivingRedstonePower(pos) ||
                    this.isPoweredByOtherRails(world, pos, state, true, 0) ||
                    this.isPoweredByOtherRails(world, pos, state, false, 0);
            if (shouldBePowered != state.get(POWERED)) {
                RailShape railShape = state.get(SHAPE);
                if (railShape.isAscending()) {
                    world.setBlockState(pos, state.with(POWERED, shouldBePowered), 3);
                    world.updateNeighborsExcept(pos.down(),self, Direction.UP);
                    world.updateNeighborsExcept(pos.up(),self, Direction.DOWN); //isAscending
                } else if (shouldBePowered) {
                    powerLane(world, pos, state, railShape);
                } else {
                    dePowerLane(world, pos, state, railShape);
                }
            }
            ci.cancel();
        }
    }

    public void powerLane(World world, BlockPos pos, BlockState mainState, RailShape railShape) {
        world.setBlockState(pos, mainState.with(POWERED, true), FORCE_PLACE);
        HashMap<BlockPos,Boolean> checkedPos = new HashMap<>();
        checkedPos.put(pos,true);
        int[] count = new int[2];
        if (railShape == RailShape.NORTH_SOUTH) { //Order: +z, -z
            for(int i = 0; i < NORTH_SOUTH_DIR.length; ++i) {
                setRailPositionsPower(world, pos, checkedPos, count, i, NORTH_SOUTH_DIR[i]);
            }
            updateRails(false, world, pos, mainState, count);
        } else if (railShape == RailShape.EAST_WEST) { //Order: -x, +x
            for(int i = 0; i < EAST_WEST_DIR.length; ++i) {
                setRailPositionsPower(world, pos, checkedPos, count, i, EAST_WEST_DIR[i]);
            }
            updateRails(true, world, pos, mainState, count);
        }
    }

    public void dePowerLane(World world, BlockPos pos, BlockState mainState, RailShape railShape) {
        world.setBlockState(pos, mainState.with(POWERED, false), FORCE_PLACE);
        int[] count = new int[2];
        if (railShape == RailShape.NORTH_SOUTH) { //Order: +z, -z
            for(int i = 0; i < NORTH_SOUTH_DIR.length; ++i) {
                setRailPositionsDePower(world, pos, count, i, NORTH_SOUTH_DIR[i]);
            }
            updateRails(false, world, pos, mainState, count);
        } else if (railShape == RailShape.EAST_WEST) { //Order: -x, +x
            for(int i = 0; i < EAST_WEST_DIR.length; ++i) {
                setRailPositionsDePower(world, pos, count, i, EAST_WEST_DIR[i]);
            }
            updateRails(true, world, pos, mainState, count);
        }
    }

    private void setRailPositionsPower(World world, BlockPos pos, HashMap<BlockPos, Boolean> checkedPos,
                                       int[] count, int i, Direction direction) {
        for (int z = 1; z < CarpetSettings.railPowerLimit; z++) {
            BlockPos newPos = pos.offset(direction,z);
            BlockState state = world.getBlockState(newPos);
            if (checkedPos.containsKey(newPos)) {
                if (!checkedPos.get(newPos)) break;
                count[i]++;
            } else if (!state.isOf(this) || state.get(POWERED) || !(
                            world.isReceivingRedstonePower(newPos) ||
                            this.isPoweredByOtherRailsFaster(world, newPos, state, true, 0, checkedPos) ||
                            this.isPoweredByOtherRailsFaster(world, newPos, state, false, 0, checkedPos)
            )) {
                checkedPos.put(newPos,false);
                break;
            } else {
                checkedPos.put(newPos,true);
                world.setBlockState(newPos, state.with(POWERED, true), FORCE_PLACE);
                count[i]++;
            }
        }
    }

    private void setRailPositionsDePower(World world, BlockPos pos, int[] count, int i, Direction direction) {
        for (int z = 1; z < CarpetSettings.railPowerLimit; z++) {
            BlockPos newPos = pos.offset(direction,z);
            BlockState state = world.getBlockState(newPos);
            if (!state.isOf(this) || !state.get(POWERED) || world.isReceivingRedstonePower(newPos) ||
                    this.isPoweredByOtherRails(world, newPos, state, true, 0) ||
                    this.isPoweredByOtherRails(world, newPos, state, false, 0)) break;
            world.setBlockState(newPos, state.with(POWERED, false), FORCE_PLACE);
            count[i]++;
        }
    }

    private void shapeUpdateEnd(World world, BlockPos pos, BlockState mainState, int endPos,
                                Direction direction, int currentPos, BlockPos blockPos) {
        if (currentPos == endPos) {
            BlockPos newPos = pos.offset(direction,currentPos+1);
            Utils.giveShapeUpdate(world, mainState, newPos, pos, direction);
            BlockState state = world.getBlockState(blockPos);
            if (state.isOf(this) && state.get(SHAPE).isAscending())
                Utils.giveShapeUpdate(world, mainState, newPos.up(), pos, direction);
        }
    }

    private void neighborUpdateEnd(World world, BlockPos pos, int endPos, Direction direction,
                                   Block block, int currentPos, BlockPos blockPos) {
        if (currentPos == endPos) {
            BlockPos newPos = pos.offset(direction,currentPos+1);
            world.updateNeighbor(newPos, block, pos);
            BlockState state = world.getBlockState(blockPos);
            if (state.isOf(this) && state.get(SHAPE).isAscending())
                world.updateNeighbor(newPos.up(), block, blockPos);
        }
    }

    private void updateRailsSectionEastWestShape(World world, BlockPos pos, int c, BlockState mainState,
                                             Direction dir, int[] count, int countAmt) {
        BlockPos pos1 = pos.offset(dir, c);
        if (c == 0 && count[1] == 0)
            Utils.giveShapeUpdate(world, mainState, pos1.offset(dir.getOpposite()), pos, dir.getOpposite());
        shapeUpdateEnd(world, pos, mainState, countAmt, dir, c, pos1);
        Utils.giveShapeUpdate(world, mainState, pos1.down(), pos, Direction.DOWN);
        Utils.giveShapeUpdate(world, mainState, pos1.up(), pos, Direction.UP);
        Utils.giveShapeUpdate(world, mainState, pos1.north(), pos, Direction.NORTH);
        Utils.giveShapeUpdate(world, mainState, pos1.south(), pos, Direction.SOUTH);
    }

    private void updateRailsSectionNorthSouthShape(World world, BlockPos pos, int c, BlockState mainState,
                                                 Direction dir, int[] count, int countAmt) {
        BlockPos pos1 = pos.offset(dir, c);
        Utils.giveShapeUpdate(world, mainState, pos1.west(), pos, Direction.WEST);
        Utils.giveShapeUpdate(world, mainState, pos1.east(), pos, Direction.EAST);
        Utils.giveShapeUpdate(world, mainState, pos1.down(), pos, Direction.DOWN);
        Utils.giveShapeUpdate(world, mainState, pos1.up(), pos, Direction.UP);
        shapeUpdateEnd(world, pos, mainState, countAmt, dir, c, pos1);
        if (c == 0 && count[1] == 0)
            Utils.giveShapeUpdate(
                    world, mainState, pos1.offset(dir.getOpposite()), pos, dir.getOpposite()
            );
    }

    private void updateRails(boolean eastWest, World world, BlockPos pos, BlockState mainState, int[] count) {
        if (eastWest) {
            for (int i = 0; i < EAST_WEST_DIR.length; ++i) {
                int countAmt = count[i];
                if (i == 1 && countAmt == 0) continue;
                Direction dir = EAST_WEST_DIR[i];
                Block block = mainState.getBlock();
                for (int c = countAmt; c >= i; c--) {
                    BlockPos p = pos.offset(dir, c);
                    if (c == 0 && count[1] == 0) world.updateNeighbor(p.offset(dir.getOpposite()), block, pos);
                    neighborUpdateEnd(world, pos, countAmt, dir, block, c, p);
                    world.updateNeighbor(p.down(), block, pos);
                    world.updateNeighbor(p.up(), block, pos);
                    world.updateNeighbor(p.north(), block, pos);
                    world.updateNeighbor(p.south(), block, pos);
                    BlockPos pos2 = pos.offset(dir, c).down();
                    world.updateNeighbor(pos2.down(), block, pos);
                    world.updateNeighbor(pos2.north(), block, pos);
                    world.updateNeighbor(pos2.south(), block, pos);
                    if (c == countAmt) world.updateNeighbor(pos.offset(dir, c + 1).down(), block, pos);
                    if (c == 0 && count[1] == 0) world.updateNeighbor(p.offset(dir.getOpposite()).down(), block, pos);
                }
                if (CFSettings.reIntroduceReverseRailUpdateOrder) {
                    for (int c = i; c <= countAmt; c++)
                        updateRailsSectionEastWestShape(world, pos, c, mainState, dir, count, countAmt);
                } else {
                    for (int c = countAmt; c >= i; c--)
                        updateRailsSectionEastWestShape(world, pos, c, mainState, dir, count, countAmt);
                }
            }
        } else {
            for(int i = 0; i < NORTH_SOUTH_DIR.length; ++i) {
                int countAmt = count[i];
                if (i == 1 && countAmt == 0) continue;
                Direction dir = NORTH_SOUTH_DIR[i];
                Block block = mainState.getBlock();
                for (int c = countAmt; c >= i; c--) {
                    BlockPos p = pos.offset(dir,c);
                    world.updateNeighbor(p.west(), block, pos);
                    world.updateNeighbor(p.east(), block, pos);
                    world.updateNeighbor(p.down(), block, pos);
                    world.updateNeighbor(p.up(), block, pos);
                    neighborUpdateEnd(world, pos, countAmt, dir, block, c, p);
                    if (c == 0 && count[1] == 0) world.updateNeighbor(p.offset(dir.getOpposite()), block, pos);
                    BlockPos pos2 = pos.offset(dir,c).down();
                    world.updateNeighbor(pos2.west(), block, pos);
                    world.updateNeighbor(pos2.east(), block, pos);
                    world.updateNeighbor(pos2.down(), block, pos);
                    if (c == countAmt) world.updateNeighbor(pos.offset(dir,c + 1).down(), block, pos);
                    if (c == 0 && count[1] == 0) world.updateNeighbor(p.offset(dir.getOpposite()).down(), block, pos);
                }
                if (CFSettings.reIntroduceReverseRailUpdateOrder) {
                    for (int c = i; c <= countAmt; c++)
                        updateRailsSectionNorthSouthShape(world, pos, c, mainState, dir, count, countAmt);
                } else {
                    for (int c = countAmt; c >= i; c--)
                        updateRailsSectionNorthSouthShape(world, pos, c, mainState, dir, count, countAmt);
                }
            }
        }
    }
}

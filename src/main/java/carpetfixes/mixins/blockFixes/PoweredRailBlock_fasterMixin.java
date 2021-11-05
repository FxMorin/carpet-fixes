package carpetfixes.mixins.blockFixes;

import carpet.CarpetSettings;
import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.*;
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

@Mixin(value = PoweredRailBlock.class,priority = 990)
public abstract class PoweredRailBlock_fasterMixin extends AbstractRailBlock {

    protected PoweredRailBlock_fasterMixin(boolean allowCurves, Settings settings) {super(allowCurves, settings);}

    @Shadow @Final public static EnumProperty<RailShape> SHAPE;
    @Shadow @Final public static BooleanProperty POWERED;

    @Shadow protected boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean bl, int distance) { return false;}

    PoweredRailBlock self = (PoweredRailBlock)(Object)this;

    private static final Direction[] EAST_WEST_DIR = new Direction[]{Direction.WEST, Direction.EAST};
    private static final Direction[] NORTH_SOUTH_DIR = new Direction[]{Direction.SOUTH, Direction.NORTH};

    private static final int FORCE_PLACE = MOVED | FORCE_STATE | NOTIFY_LISTENERS;


    @Inject(
            method = "updateBlockState(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor, CallbackInfo ci) {
        if (CarpetFixesSettings.optimizedPoweredRails) {
            boolean shouldBePowered = world.isReceivingRedstonePower(pos) || this.isPoweredByOtherRails(world, pos, state, true, 0) || this.isPoweredByOtherRails(world, pos, state, false, 0);
            if (shouldBePowered != state.get(POWERED)) {
                RailShape railShape = state.get(SHAPE);
                if (!shouldBePowered || railShape.isAscending()) {
                    world.setBlockState(pos, state.with(POWERED, shouldBePowered), 3);
                    world.updateNeighborsExcept(pos.down(),self, Direction.UP);
                    if (railShape.isAscending()) {
                        world.updateNeighborsExcept(pos.down(),self, Direction.DOWN);
                    }
                } else {
                    powerLane(world, pos, state, railShape);
                }
            }
            ci.cancel();
        }
    }

    public void powerLane(World world, BlockPos pos, BlockState mainstate, RailShape railShape) {
        world.setBlockState(pos, mainstate.with(POWERED, true), FORCE_PLACE);
        if (railShape == RailShape.NORTH_SOUTH) { //Order: +z, -z
            int[] zcount = new int[2];
            for(int i = 0; i < NORTH_SOUTH_DIR.length; ++i) {
                Direction direction = NORTH_SOUTH_DIR[i];
                for (int z = 1; z < CarpetSettings.railPowerLimit; z++) {
                    BlockPos newPos = pos.offset(direction,z);
                    BlockState state = world.getBlockState(newPos);
                    if (!state.isOf(this) || state.get(POWERED) || !(world.isReceivingRedstonePower(newPos) || this.isPoweredByOtherRails(world, newPos, state, true, 0) || this.isPoweredByOtherRails(world, newPos, state, false, 0))) break;
                    world.setBlockState(newPos, state.with(POWERED, true), FORCE_PLACE);
                    zcount[i]++;
                }
            }
            for(int i = 0; i < NORTH_SOUTH_DIR.length; ++i) {
                int z11 = zcount[i];
                if (z11 != 0) {
                    Direction direction = NORTH_SOUTH_DIR[i];
                    Block block = mainstate.getBlock();
                    for (int zu1 = z11; zu1 >= 0; zu1--) {
                        BlockPos pos1 = pos.offset(direction,zu1);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.west(), block, pos, Direction.WEST);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.east(), block, pos, Direction.EAST);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.down(), block, pos, Direction.DOWN);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.up(), block, pos, Direction.UP);
                        if (zu1 == z11) {
                            BlockPos newPos = pos.offset(direction,zu1 + 1);
                            Utils.updateNeighborWithShape(world, mainstate, newPos, block, pos, direction);
                            BlockState state = world.getBlockState(pos1);
                            if (state.isOf(this) && state.get(SHAPE).isAscending()) {
                                world.updateNeighbor(newPos.up(), block, pos1);
                            }
                        }
                        if (zu1 == 0 && zcount[i == 0 ? 1 : 0] == 0) {
                            Utils.updateNeighborWithShape(world, mainstate, pos1.offset(direction), block, pos, direction);
                        }
                        BlockPos pos2 = pos.offset(direction,zu1).down();
                        Utils.updateNeighborWithShape(world, mainstate, pos2.west(), block, pos, Direction.WEST);
                        Utils.updateNeighborWithShape(world, mainstate, pos2.east(), block, pos, Direction.EAST);
                        Utils.updateNeighborWithShape(world, mainstate, pos2.down(), block, pos, Direction.DOWN);
                        if (zu1 == z11) {
                            Utils.updateNeighborWithShape(world, mainstate, pos.offset(direction.getOpposite(),zu1 + 1).down(), block, pos, Direction.DOWN);
                        }
                    }
                }
            }
        } else if (railShape == RailShape.EAST_WEST) { //Order: -x, +x
            int[] xcount = new int[2];
            for(int i = 0; i < EAST_WEST_DIR.length; ++i) {
                Direction direction = EAST_WEST_DIR[i];
                for (int z = 1; z < CarpetSettings.railPowerLimit; z++) {
                    BlockPos newPos = pos.offset(direction,z);
                    BlockState state = world.getBlockState(newPos);
                    if (!state.isOf(this) || state.get(POWERED) || !(world.isReceivingRedstonePower(newPos) || this.isPoweredByOtherRails(world, newPos, state, true, 0) || this.isPoweredByOtherRails(world, newPos, state, false, 0))) break;
                    world.setBlockState(newPos, state.with(POWERED, true), FORCE_PLACE);
                    xcount[i]++;
                }
            }
            for(int i = 0; i < EAST_WEST_DIR.length; ++i) {
                int x11 = xcount[i];
                if (x11 != 0) {
                    Direction direction = EAST_WEST_DIR[i];
                    Block block = mainstate.getBlock();
                    for (int xu1 = x11; xu1 >= 0; xu1--) {
                        BlockPos pos1 = pos.offset(direction,xu1);
                        if (xu1 == x11) {
                            BlockPos newPos = pos.offset(direction,xu1+1);
                            Utils.updateNeighborWithShape(world, mainstate, newPos, block, pos, direction);
                            BlockState state = world.getBlockState(pos1);
                            if (state.isOf(this) && state.get(SHAPE).isAscending()) {
                                world.updateNeighbor(newPos.up(), block, pos1);
                            }
                        }
                        if (xu1 == 0 && xcount[i == 0 ? 1 : 0] == 0) {
                            Utils.updateNeighborWithShape(world, mainstate, pos1.offset(direction.getOpposite()), block, pos, direction.getOpposite());
                        }
                        Utils.updateNeighborWithShape(world, mainstate, pos1.down(), block, pos, Direction.DOWN);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.up(), block, pos, Direction.UP);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.north(), block, pos, Direction.NORTH);
                        Utils.updateNeighborWithShape(world, mainstate, pos1.south(), block, pos, Direction.SOUTH);
                        BlockPos pos2 = pos.offset(direction,xu1).down();
                        Utils.updateNeighborWithShape(world, mainstate, pos2.down(), block, pos, Direction.DOWN);
                        Utils.updateNeighborWithShape(world, mainstate, pos2.north(), block, pos, Direction.NORTH);
                        Utils.updateNeighborWithShape(world, mainstate, pos2.south(), block, pos, Direction.SOUTH);
                        if (xu1 == x11) {
                            Utils.updateNeighborWithShape(world, mainstate, pos.offset(direction,xu1+1).down(), block, pos, Direction.DOWN);
                        }
                    }
                }
            }
        }
    }
}

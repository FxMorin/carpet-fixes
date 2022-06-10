package carpetfixes.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class CollisionUtils {

    public static boolean isEntityTouchingState(BlockView world, BlockPos pos, Entity entity, BlockState state) {
        return VoxelShapes.matchesAnywhere(
                state.getCollisionShape(world, pos).offset(pos.getX(), pos.getY(), pos.getZ()),
                VoxelShapes.cuboid(entity.getBoundingBox().expand(0.0001D)),
                BooleanBiFunction.AND
        );
    }

    public static BlockState pushEntitiesUpBeforeBlockChange(BlockState from, BlockState to, World world, BlockPos pos) {
        VoxelShape voxelShape = VoxelShapes.combine(
                from.getCollisionShape(world, pos),
                to.getCollisionShape(world, pos),
                BooleanBiFunction.ONLY_SECOND
        ).offset(pos.getX(), pos.getY(), pos.getZ());
        if (voxelShape.isEmpty()) {
            return to;
        } else {
            for(Entity entity : world.getOtherEntities(null, voxelShape.getBoundingBox())) {
                double yMovement = VoxelShapes.calculateMaxOffset(
                        Direction.Axis.Y,
                        entity.getBoundingBox().offset(0.0, 1.0, 0.0),
                        List.of(voxelShape),
                        -1.0
                );
                entity.setPos(entity.getX(), entity.getY() + 1.0 + yMovement, entity.getZ());
            }
            return to;
        }
    }

    public static BlockState pushEntitiesUpBeforeBlockChangeVelocity(BlockState from, BlockState to, World world, BlockPos pos) {
        VoxelShape voxelShape = VoxelShapes.combine(
                from.getCollisionShape(world, pos),
                to.getCollisionShape(world, pos),
                BooleanBiFunction.ONLY_SECOND
        ).offset(pos.getX(), pos.getY(), pos.getZ());
        if (voxelShape.isEmpty()) {
            return to;
        } else {
            for(Entity entity : world.getOtherEntities(null, voxelShape.getBoundingBox())) {
                double yMovement = VoxelShapes.calculateMaxOffset(
                        Direction.Axis.Y,
                        entity.getBoundingBox().offset(0.0, 1.0, 0.0),
                        List.of(voxelShape),
                        -1.0
                );
                Vec3d currentVelocity = entity.getVelocity();
                entity.setVelocity(currentVelocity.getX(),1.0 + yMovement,currentVelocity.getZ());
                //entity.setPos(entity.getX(), entity.getY() + 1.0 + yMovement, entity.getZ());
            }
            return to;
        }
    }
}

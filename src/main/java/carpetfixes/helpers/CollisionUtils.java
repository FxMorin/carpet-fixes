package carpetfixes.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CollisionUtils {

    public static boolean isEntityTouchingState(BlockView world, BlockPos pos, Entity entity, BlockState state) {
        return VoxelShapes.matchesAnywhere(
                state.getCollisionShape(world, pos).offset(pos.getX(), pos.getY(), pos.getZ()),
                VoxelShapes.cuboid(entity.getBoundingBox().expand(0.0001D)),
                BooleanBiFunction.AND
        );
    }
}

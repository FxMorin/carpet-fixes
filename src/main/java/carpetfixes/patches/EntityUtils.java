package carpetfixes.patches;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.util.math.Vec3d;

public interface EntityUtils {
    void calculateDimensionsWithoutHeight();
    void calculateDimensionsToDimensions(EntityDimensions entityDimensions);
    Vec3d adjustMovementForCollisionsAtPos(EntityDimensions entityDimensions, Vec3d movement, Vec3d pos, boolean includeEntities);
}

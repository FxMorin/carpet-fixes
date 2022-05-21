package carpetfixes.patches;

import net.minecraft.entity.EntityDimensions;

public interface EntityUtils {
    void calculateDimensionsWithoutHeight();
    void calculateDimensionsToDimensions(EntityDimensions entityDimensions);
}

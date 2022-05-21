package carpetfixes.mixins.utils;

import carpetfixes.patches.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class Entity_dimensionsMixin implements EntityUtils {

    private final Entity self = (Entity)(Object)this;

    @Shadow
    private EntityDimensions dimensions;

    @Shadow
    private float standingEyeHeight;

    @Shadow
    public World world;

    @Shadow
    protected boolean firstUpdate;

    @Shadow
    public boolean noClip;

    @Shadow
    private BlockPos blockPos;

    @Shadow
    public abstract EntityPose getPose();

    @Shadow
    public abstract EntityDimensions getDimensions(EntityPose pose);

    @Shadow
    protected abstract float getEyeHeight(EntityPose pose, EntityDimensions dimensions);

    @Shadow
    public abstract Vec3d getPos();

    @Shadow
    public abstract void setPosition(Vec3d pos);

    @Shadow
    protected abstract void refreshPosition();

    @Shadow
    public abstract BlockPos getBlockPos();

    @Override
    public void calculateDimensionsWithoutHeight() {
        EntityDimensions dim = this.dimensions;
        EntityPose entityPose = this.getPose();
        EntityDimensions dim2 = this.getDimensions(entityPose);
        this.dimensions = dim2;
        this.standingEyeHeight = this.getEyeHeight(entityPose, dim2);
        this.refreshPosition();
        if ( // Skip calculating the new dimensions if conditions are not met
                !this.world.isClient
                && !this.firstUpdate
                && !this.noClip
                && (double)dim2.width <= 4.0 && (double)dim2.height <= 4.0
                && (dim2.width > dim.width || dim2.height > dim.height)
                && !(self instanceof PlayerEntity)
        ) {
            Vec3d vec3d = this.getPos().add(0.0, (double)dim.height / 2.0, 0.0); // Add half the height
            double d = (double)Math.max(0.0F, dim2.width - dim.width) + 1.0E-6; // Get change in width
            double e = (double)Math.max(0.0F, dim2.height - dim.height) + 1.0E-6; // Get change in height
            VoxelShape voxelShape = VoxelShapes.cuboid(Box.of(vec3d, d, e, d));
            // Don't check height by using original dim.height instead of dim2.height here
            double lowerHeight = this.world.isTopSolid(this.blockPos.down(),self) ?
                    (double)(-dim.height) / 2.0 : (double)(-dim2.height) / 2.0;
            this.world.findClosestCollision(self, voxelShape, vec3d, dim2.width, dim.height, dim2.width)
                    .ifPresent(pos -> this.setPosition(pos.add(0.0, lowerHeight, 0.0)));
        }
    }
}

package carpetfixes.mixins.utils;

import carpetfixes.mixins.accessors.EntityAccessor;
import carpetfixes.patches.ExtendedEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Entity.class)
public abstract class Entity_dimensionsMixin implements ExtendedEntity {

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

    @Shadow
    protected boolean onGround;

    @Shadow
    public float stepHeight;


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


    @Override
    public void calculateDimensionsToDimensions(EntityDimensions dim2) {
        EntityPose entityPose = this.getPose();
        EntityDimensions dim = this.getDimensions(entityPose);
        this.dimensions = dim2;
        this.refreshPosition();
        if (!this.world.isClient
                && (double)dim2.width <= 4.0 && (double)dim2.height <= 4.0
                && (dim2.width > dim.width || dim2.height > dim.height)
                && !(self instanceof PlayerEntity)) {
            Vec3d vec3d = this.getPos();
            double d = (double)Math.max(0.0F, dim2.width - dim.width) + 1.0E-6;
            double e = (double)Math.max(0.0F, dim2.height - dim.height) + 1.0E-6;
            VoxelShape voxelShape = VoxelShapes.cuboid(Box.of(vec3d, d, e, d));
            this.world
                    .findClosestCollision(self, voxelShape, vec3d, dim2.width, dim2.height, dim2.width)
                    .ifPresent(this::setPosition);
        }
    }


    @Override
    public Vec3d adjustMovementForCollisionsAtPos(EntityDimensions dimensions, Vec3d movement,
                                                  Vec3d pos, boolean includeEntities) {
        Box box = dimensions.getBoxAt(pos);
        List<VoxelShape> entityCollisionsList = includeEntities ?
                this.world.getEntityCollisions(self, box.stretch(movement)) : List.of();
        Vec3d vec3d = movement.lengthSquared() == 0.0 ? movement :
                adjustMovementForCollisionsAtPos(self, pos, movement, box, this.world, entityCollisionsList);
        boolean xChanged = movement.x != vec3d.x;
        boolean yChanged = movement.y != vec3d.y;
        boolean zChanged = movement.z != vec3d.z;
        boolean groundCollisions = this.onGround || yChanged && movement.y < 0.0;
        if (this.stepHeight > 0.0F && groundCollisions && (xChanged || zChanged)) {
            Vec3d vec3d2 = adjustMovementForCollisionsAtPos(
                    self,
                    pos,
                    new Vec3d(movement.x, this.stepHeight, movement.z),
                    box,
                    this.world,
                    entityCollisionsList
            );
            Vec3d vec3d3 = adjustMovementForCollisionsAtPos(
                    self,
                    pos,
                    new Vec3d(0.0, this.stepHeight, 0.0),
                    box.stretch(movement.x, 0.0, movement.z),
                    this.world,
                    entityCollisionsList
            );
            if (vec3d3.y < (double)this.stepHeight) {
                Vec3d vec3d4 = adjustMovementForCollisionsAtPos(
                        self,
                        pos,
                        new Vec3d(movement.x, 0.0, movement.z),
                        box.offset(vec3d3),
                        this.world,
                        entityCollisionsList
                ).add(vec3d3);
                if (vec3d4.horizontalLengthSquared() > vec3d2.horizontalLengthSquared()) vec3d2 = vec3d4;
            }
            if (vec3d2.horizontalLengthSquared() > vec3d.horizontalLengthSquared())
                return vec3d2.add(adjustMovementForCollisionsAtPos(
                        self,
                        pos,
                        new Vec3d(0.0, -vec3d2.y + movement.y, 0.0),
                        box.offset(vec3d2),
                        this.world,
                        entityCollisionsList
                ));
        }
        return vec3d;
    }

    private static Vec3d adjustMovementForCollisionsAtPos(@Nullable Entity entity, Vec3d pos, Vec3d movement,
                                                          Box entityBounds, World world,
                                                          List<VoxelShape> collisions) {
        ImmutableList.Builder<VoxelShape> builder = ImmutableList.builderWithExpectedSize(collisions.size() + 1);
        if (!collisions.isEmpty()) builder.addAll(collisions);
        WorldBorder worldBorder = world.getWorldBorder();
        boolean bl = entity != null && canWorldBorderCollideAtPos(worldBorder, pos, entityBounds.stretch(movement));
        if (bl) builder.add(worldBorder.asVoxelShape());
        builder.addAll(world.getBlockCollisions(entity, entityBounds.stretch(movement)));
        return EntityAccessor.invokeAdjustMovementForCollisions(movement, entityBounds, builder.build());
    }

    private static boolean canWorldBorderCollideAtPos(WorldBorder worldBorder, Vec3d pos, Box box) {
        double d = Math.max(MathHelper.absMax(box.getXLength(), box.getZLength()), 1.0);
        return worldBorder.getDistanceInsideBorder(pos.getX(), pos.getZ()) < d * 2.0 &&
                worldBorder.contains(pos.getX(), pos.getZ(), d);
    }
}

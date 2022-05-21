package carpetfixes.mixins.accessors;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.gen.random.AbstractRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.UUID;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("random")
    AbstractRandom getRandom();
    @Accessor("uuid")
    void setUuid(UUID uuid);

    @Invoker("adjustMovementForCollisions")
    static Vec3d invokeAdjustMovementForCollisions(Vec3d movement, Box entityBounds, List<VoxelShape> collisions) {
        throw new AssertionError();
    }
}

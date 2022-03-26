package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PistonBlockEntity.class)
public abstract class PistonBlockEntity_translocationMixin {


    @Inject(
            method = "pushEntities(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "FLnet/minecraft/block/entity/PistonBlockEntity;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void simulatePistonTranslocation(World world, BlockPos pos, float f,
                                                    PistonBlockEntity pbe, CallbackInfo ci) {
        if (CFSettings.reIntroducePistonTranslocation) {
            ci.cancel();
            VoxelShape vs = pbe.getPushedBlock().getCollisionShape(world, pos);
            if (vs.isEmpty()) return;
            Box box = vs.getBoundingBox().offset(pos).expand(0.01D); // Cheating ;)
            List<Entity> list = world.getOtherEntities(null, box);
            if (list.isEmpty()) return;
            boolean isSlime = pbe.getPushedBlock().isOf(Blocks.SLIME_BLOCK);
            Direction dir = pbe.getMovementDirection();
            for (Entity entity : list) {
                if (entity.getPistonBehavior() != PistonBehavior.IGNORE) {
                    if (isSlime) { //No player check, just like before
                        Vec3d vec3d = entity.getVelocity();
                        switch (dir.getAxis()) {
                            case X -> entity.setVelocity(dir.getOffsetX(), vec3d.y, vec3d.z);
                            case Y -> entity.setVelocity(vec3d.x, dir.getOffsetY(), vec3d.z);
                            case Z -> entity.setVelocity(vec3d.x, vec3d.y, dir.getOffsetZ());
                        }
                    }
                    double x = 0.0D, y = 0.0D, z = 0.0D;
                    Box entityBox = entity.getBoundingBox();
                    boolean positive = dir.getDirection() == Direction.AxisDirection.POSITIVE;
                    switch (dir.getAxis()) {
                        case X -> x = (positive ? box.maxX - entityBox.minX : entityBox.maxX - box.minX) + 0.1D;
                        case Y -> y = (positive ? box.maxY - entityBox.minY : entityBox.maxY - box.minY) + 0.1D;
                        case Z -> z = (positive ? box.maxZ - entityBox.minZ : entityBox.maxZ - box.minZ) + 0.1D;
                    }
                    entity.move(MovementType.SELF, new Vec3d(
                            x * (double) dir.getOffsetX(),
                            y * (double) dir.getOffsetY(),
                            z * (double) dir.getOffsetZ()
                    ));
                }
            }
        }
    }
}

package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

/**
 * Structures don't create passenger entities since they call the wrong method.
 */

@Mixin(StructureTemplate.class)
public class StructureTemplate_entityPassengersMixin {


    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(
            method = "spawnEntities",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/structure/StructureTemplate;" +
                            "getEntity(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/nbt/NbtCompound;)" +
                            "Ljava/util/Optional;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void getEntityWithPassengers(ServerWorldAccess world, BlockPos pos, BlockMirror mirror,
                                         BlockRotation rotation, BlockPos pivot, BlockBox area, boolean initializeMobs,
                                         CallbackInfo ci, Iterator<StructureTemplate.StructureEntityInfo> iterator,
                                         StructureTemplate.StructureEntityInfo entityInfo, NbtCompound nbtCompound,
                                         Vec3d vec3d, Vec3d vec3d2, NbtList nbtList) {
        if (CFSettings.structuresIgnorePassengersFix) {
            Entity entity = EntityType.loadEntityWithPassengers(nbtCompound, world.toServerWorld(), e -> e);
            if (entity != null) {
                float f = entity.applyRotation(rotation);
                f += entity.applyMirror(mirror) - entity.getYaw();
                entity.refreshPositionAndAngles(vec3d2.x, vec3d2.y, vec3d2.z, f, entity.getPitch());
                if (initializeMobs && entity instanceof MobEntity) {
                    ((MobEntity) entity).initialize(
                            world,
                            world.getLocalDifficulty(BlockPos.ofFloored(vec3d2)),
                            SpawnReason.STRUCTURE,
                            null,
                            nbtCompound
                    );
                }
                world.spawnEntityAndPassengers(entity);
            }
            ci.cancel();
        }
    }
}

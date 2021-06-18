package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CatSpawner.class)
public class CatSpawner_incorrectCatMixin {
    @Redirect(method="spawn(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/server/world/ServerWorld;)I",at=@At(value="INVOKE",target="Lnet/minecraft/entity/passive/CatEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;"))
    private EntityData weDontWantThis(CatEntity catEntity, ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        if (!CarpetFixesSettings.witchHutsSpawnIncorrectCatFix) {
            return catEntity.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        }
        return null;
    }

    @Redirect(method= "spawn(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/server/world/ServerWorld;)I",at=@At(value="INVOKE",target="Lnet/minecraft/entity/Entity;refreshPositionAndAngles(Lnet/minecraft/util/math/BlockPos;FF)V"))
    private void weDoWantToDoItHere(Entity entity, BlockPos pos, float yaw, float pitch) {
        if (!CarpetFixesSettings.witchHutsSpawnIncorrectCatFix) {
            entity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
        }
    }

    @Inject(method="spawn(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/server/world/ServerWorld;)I",locals = LocalCapture.CAPTURE_FAILSOFT,at=@At(value="INVOKE",target="Lnet/minecraft/entity/passive/CatEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;",shift= At.Shift.BEFORE))
    private void spawn(BlockPos pos, ServerWorld world, CallbackInfoReturnable<Integer> cir, CatEntity catEntity) {
        if (CarpetFixesSettings.witchHutsSpawnIncorrectCatFix) {
            catEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            catEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
        }
    }
}

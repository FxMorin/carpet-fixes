package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.CollisionView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_spawnPlatformMixin extends PlayerEntity {

    public ServerPlayerEntity_spawnPlatformMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Shadow
    protected abstract void createEndSpawnPlatform(ServerWorld world, BlockPos centerPos);

    // Only replace end stone with air
    private void createEndSpawnObsidian(ServerWorld world, BlockPos centerPos) {
        BlockPos.Mutable mutable = centerPos.mutableCopy();
        BlockState state = Blocks.OBSIDIAN.getDefaultState();
        BlockState airState = Blocks.AIR.getDefaultState();
        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                for(int k = 0; k <= 2; ++k) {
                    BlockPos pos = mutable.set(centerPos).move(j, k, i);
                    if (world.getBlockState(pos).isOf(Blocks.END_STONE)) world.setBlockState(pos, airState);
                }
                world.setBlockState(mutable.set(centerPos).move(j, -1, i), state);
            }
        }
    }

    private void breakAValidBox(ServerWorld world, BlockPos centerPos) {
        BlockPos.Mutable mutable = centerPos.mutableCopy();
        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                for(int k = 0; k <= 2; ++k) {
                    world.breakBlock(mutable.set(centerPos).move(j, k, i), true);
                }
            }
        }
    }

    // Attempt to find a valid location to spawn, with an outwards search
    private static Optional<Vec3d> findValidSpawnPosition(EntityType<?> type, CollisionView world, BlockPos pos) {
        for(BlockPos blockPos : BlockPos.iterateOutwards(pos, 2, 0, 2)) {
            Vec3d vec3d = Dismounting.findRespawnPos(type, world, blockPos, true);
            if (vec3d != null) return Optional.of(vec3d);
        }
        return Optional.empty();
    }


    @Inject(
            method = "getTeleportTarget",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getCustomTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
        if (CFSettings.obsidianPlatformDestroysBlocksFix) {
            TeleportTarget teleportTarget = super.getTeleportTarget(destination);
            if (teleportTarget != null && this.world.getRegistryKey() == World.OVERWORLD && destination.getRegistryKey() == World.END) {
                Vec3d vec3d = teleportTarget.position.add(0.0, -1.0, 0.0);
                BlockPos centerPos = new BlockPos(vec3d);
                createEndSpawnObsidian(destination, centerPos); // Create Floor
                // Find a valid place to teleport to on the platform
                Optional<Vec3d> opt = findValidSpawnPosition(EntityType.PLAYER, destination, centerPos);
                if (opt.isPresent()) {
                    vec3d = opt.get();
                } else {
                    breakAValidBox(destination, centerPos);
                }
                cir.setReturnValue(new TeleportTarget(vec3d, Vec3d.ZERO, 90.0F, 0.0F));
            } else {
                cir.setReturnValue(teleportTarget);
            }
        }
    }


    @Redirect(
            method = "moveToWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;" +
                            "createEndSpawnPlatform(Lnet/minecraft/server/world/ServerWorld;" +
                            "Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    private void dontRecreateObsidianPlatform(ServerPlayerEntity player, ServerWorld world, BlockPos centerPos) {
        if (CFSettings.obsidianPlatformDestroysBlocksFix) return;
        this.createEndSpawnPlatform(world, centerPos);
    }
}

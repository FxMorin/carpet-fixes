package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents you from being able to change dimension while sleeping, fixing a 8x coord multiplication exploits
 */

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_bedTeleportMixin extends PlayerEntity {


    public ServerPlayerEntity_bedTeleportMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
            method = "moveToWorld",
            at = @At("HEAD"),
            cancellable = true
    )
    private void moveToWorldIfNotSleeping(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        if (CFSettings.bedTeleportExploitFix && this.isSleeping()) cir.setReturnValue(this);
    }
}

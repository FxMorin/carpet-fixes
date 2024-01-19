package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes the end portal removing all your status effects
 */

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_endPortalEffectsMixin extends PlayerEntity {

    public ServerPlayerEntity_endPortalEffectsMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    @Inject(
            method = "copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;setHealth(F)V",
                    shift = At.Shift.AFTER
            )
    )
    private void cf$onCopyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (CFSettings.endPortalRemovesEffectsFix) {
            this.activeStatusEffects.putAll(oldPlayer.activeStatusEffects);
        }
    }
}

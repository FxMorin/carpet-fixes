package carpetfixes.mixins.playerFixes;

import carpetfixes.CarpetFixesSettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_endPortalEffectsMixin extends PlayerEntity {

    public ServerPlayerEntity_endPortalEffectsMixin(World world, BlockPos pos, float yaw, GameProfile profile) {super(world, pos, yaw, profile);}

    ServerPlayerEntity self = (ServerPlayerEntity)(Object)this;


    @Inject(
            method= "copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/server/network/ServerPlayerEntity;setHealth(F)V",
                    shift= At.Shift.AFTER
            ))
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (CarpetFixesSettings.endPortalRemovesEffectsFix) {
            self.activeStatusEffects.putAll(oldPlayer.activeStatusEffects);
        }
    }
}

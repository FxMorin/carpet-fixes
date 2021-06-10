package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_vehicleFloatingMixin {

    @Shadow private boolean floating;
    @Shadow public ServerPlayerEntity player;

    /**
     * You can get kicked for flying by mounding an entity after jumping, this is
     * because the server does not realize that you mounting an entity means you
     * are no longer jumping. So this fix is as simple as telling the server
     * that if they are riding an entity, they should not be considered floating
     */
    @Inject(method = "onVehicleMove(Lnet/minecraft/network/packet/c2s/play/VehicleMoveC2SPacket;)V", at = @At("RETURN"))
    public void IfVehicleMovedPlayerNotFlying(VehicleMoveC2SPacket packet, CallbackInfo ci){
        if (CarpetFixesSettings.mountingFlyingTooLongFix && this.player.hasVehicle() && this.player.getVehicle() != this.player) {
            this.floating = false;
        }
    }
}

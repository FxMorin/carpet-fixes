package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_vehicleFloatingMixin {

    @Shadow private boolean floating;
    @Shadow private boolean ridingEntity;

    @Inject(method = "onVehicleMove(Lnet/minecraft/network/packet/c2s/play/VehicleMoveC2SPacket;)V", at = @At("RETURN"))
    public void IfVehicleMovedPlayerNotFlying(VehicleMoveC2SPacket packet, CallbackInfo ci){
        if (CarpetFixesSettings.mountingFlyingTooLongFix && this.ridingEntity) {
            this.floating = false;
        }
    }
}

package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import carpetfixes.CarpetFixesServer;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents the nocom exploit from working
 */

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_nocomMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Unique
    private int cf$illegalInteractionAttempts = 0;
    @Unique
    private long cf$timeoutUntil = 0;
    @Unique
    private long cf$resetAfter = 0;


    @Unique
    private void cf$addIllegalAction(long currentTime) {
        cf$illegalInteractionAttempts++;
        //Make sure people with terrible connections are affected after long periods of time (reset every 5min)
        if (cf$resetAfter <= currentTime) {
            cf$resetAfter = currentTime + 6000;
            cf$illegalInteractionAttempts = 0;
        } else {
            if (cf$illegalInteractionAttempts > 50) {
                cf$resetAfter = this.player.getWorld().getTime() + 12000; // (10m)
                cf$illegalInteractionAttempts = 0;
                //Disable players ability to run playerInteractBlock for 1 min
                cf$timeoutUntil = this.player.getWorld().getTime() + 1200;
                CarpetFixesServer.LOGGER.warn(player.getName().getString() + " is probably using a nocom" +
                        " exploit in an attempt to crash the server");
            }
        }
    }


    @Inject(
            method = "onPlayerInteractBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(" +
                            "Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;" +
                            "Lnet/minecraft/server/world/ServerWorld;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void cf$patch(PlayerInteractBlockC2SPacket p, CallbackInfo ci) {
        if (!CFSettings.nocomExploitFix || this.player.getWorld().getServer() == null) {
            return;
        }
        long currentTime = this.player.getWorld().getTime();
        if (cf$timeoutUntil != 0) { // If player is timed out due to using this action illegally over 50 times
            if (cf$timeoutUntil >= currentTime) {
                ci.cancel();
                return;
            }
            cf$timeoutUntil = 0;
        }
        int currentDistance = p.getBlockHitResult().getBlockPos().getManhattanDistance(player.getBlockPos());
        //By doing this check first, we are able to improve performance, which is critical for this type of attack
        if (currentDistance > 256) { //If further than 16 chunks, don't allow it, and run addIllegalAction instantly
            cf$addIllegalAction(currentTime);
            ci.cancel(); //It's high enough that we're pretty sure there hacking, so we can cancel it
        } else if (currentDistance >
                (this.player.getWorld().getServer().getPlayerManager().getViewDistance() - 2) << 4) {
            cf$addIllegalAction(currentTime); //If the action was (ViewDistance-2)*16 blocks away, that's kinda sus
        }
    }
}

package carpetfixes.mixins.gameEventFixes.stepEvent;

import carpetfixes.patches.ServerPlayerEntityEmitStep;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_stepEventMixin {

    @Shadow
    public ServerPlayerEntity player;


    @Inject(
            method = "onPlayerMove(Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;setOnGround(Z)V"
            )
    )
    public void afterOnGround(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (((ServerPlayerEntityEmitStep)this.player).shouldStep()) this.player.emitGameEvent(GameEvent.STEP);
    }
}

package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_merchantMixin {


    @Inject(
            method = "onSelectMerchantTrade(Lnet/minecraft/network/packet/c2s/play/SelectMerchantTradeC2SPacket;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;" +
                            "currentScreenHandler:Lnet/minecraft/screen/ScreenHandler;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void dontRunIfNegativeTradeValue(SelectMerchantTradeC2SPacket packet, CallbackInfo ci, int i) {
        if (CFSettings.merchantTradePacketExceptionFix && i < 0) ci.cancel();
    }
}

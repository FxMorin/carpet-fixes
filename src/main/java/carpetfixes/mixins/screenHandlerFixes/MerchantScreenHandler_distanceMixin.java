package carpetfixes.mixins.screenHandlerFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.village.Merchant;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantScreenHandler.class)
public class MerchantScreenHandler_distanceMixin {

    @Shadow
    @Final
    private Merchant merchant;


    @Inject(
            method = "canUse(Lnet/minecraft/entity/player/PlayerEntity;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void canUseIfWithinDistance(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.voidTradingFix && cir.getReturnValue()) {
            if (this.merchant instanceof MerchantEntity merchGuy && merchGuy.hasCustomer()) {
                double distance = merchGuy.getBlockPos().getManhattanDistance(merchGuy.getCustomer().getBlockPos());
                cir.setReturnValue(distance <= 64.0D);
            }
        }
    }
}

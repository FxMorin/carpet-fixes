package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes trade offers indefinitely increasing in demand, causing multiple weird behaviors
 */

@Mixin(TradeOffer.class)
public class TradeOffer_demandUnderflowMixin {

    @Shadow
    private int demandBonus;

    @Shadow
    private int uses;

    @Shadow
    @Final
    private int maxUses;


    @Inject(
            method = "updateDemandBonus()V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onUpdateDemandBonus(CallbackInfo ci) {
        if (CFSettings.tradeDemandDecreasesIndefinitelyFix) {
            this.demandBonus = Math.max(0,this.demandBonus + this.uses - (this.maxUses - this.uses));
            ci.cancel();
        }
    }
}

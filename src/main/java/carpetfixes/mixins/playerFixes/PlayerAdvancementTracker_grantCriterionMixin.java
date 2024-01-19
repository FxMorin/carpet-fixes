package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes an Issue allowing for players in Spectator mode to be granted Advancement Criteria
 */


@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTracker_grantCriterionMixin {

    @Shadow
    private ServerPlayerEntity owner;

    @Inject(
            method = "grantCriterion",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$grantCriterion(AdvancementEntry advancement, String criterionName,
                                   CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.spectatorAdvancementGrantingFix &&
                owner.interactionManager.getGameMode().equals(GameMode.SPECTATOR)) {
            cir.cancel();
        }
    }
}

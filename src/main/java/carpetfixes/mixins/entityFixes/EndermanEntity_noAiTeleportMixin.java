package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes enderman being able to teleport when they are set to noAI
 */

@Mixin(EndermanEntity.class)
public class EndermanEntity_noAiTeleportMixin {

    @Inject(
            method = "teleportRandomly",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$cancelTeleportNoAI(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.endermanTeleportWithoutAIFix && ((EndermanEntity)(Object)this).isAiDisabled()) {
            cir.setReturnValue(false);
        }
    }
}

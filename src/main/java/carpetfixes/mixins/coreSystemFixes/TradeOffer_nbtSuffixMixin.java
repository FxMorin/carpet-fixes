package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TradeOffer.class)
public class TradeOffer_nbtSuffixMixin {


    @ModifyArg(
            method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;I)Z"
            ),
            index = 1
    )
    private static int incorrectNbtCheck(int value) {
        return CarpetFixesSettings.incorrectNbtChecks ? 99 : value;
    }
}

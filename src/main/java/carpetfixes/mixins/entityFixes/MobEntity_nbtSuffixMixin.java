package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Fix status effects checking for the wrong nbt type
 */

@Mixin(MobEntity.class)
public class MobEntity_nbtSuffixMixin {


    @ModifyArg(
            method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;I)Z",
                    ordinal = 0
            ),
            index = 1
    )
    private int cf$incorrectNbtCheck(int value) {
        return CFSettings.incorrectNbtChecks ? 99 : 1;
    }
}

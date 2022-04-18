package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntity_deathTimeMixin {

    // Would be better if the devs would have the deathTime == 20 in updatePostDeath be >= instead


    @Redirect(
            method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;getShort(Ljava/lang/String;)S",
                    ordinal = 1
            )
    )
    private short minTheDeathTime(NbtCompound instance, String key) {
        short val = instance.getShort(key);
        return CFSettings.deathTimeCorruptsMobsFix ? (short)Math.min(val, 19) : val;
    }
}

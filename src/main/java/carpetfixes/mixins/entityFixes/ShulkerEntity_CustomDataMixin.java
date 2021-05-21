package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerEntity.class)
public class ShulkerEntity_CustomDataMixin {
    @Inject(
            method = "writeCustomDataToTag",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/ShulkerEntity;getAttachedBlock()Lnet/minecraft/util/math/BlockPos;"
            ),
            cancellable = true
    )
    private void onWriteCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        if (CarpetFixesSettings.shulkerTeleportFix) {
            ci.cancel();
        }
    }
}

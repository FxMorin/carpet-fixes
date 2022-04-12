package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_lastProgressMixin {

    @Shadow
    private float lastProgress;

    @Shadow
    private float progress;


    @Inject(
            method = "readNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;getBoolean(Ljava/lang/String;)Z",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void getLastProgress(NbtCompound nbt, CallbackInfo ci) {
        if (CFSettings.pistonReloadInconsistencyFix) this.lastProgress = nbt.getFloat("lastProgress");
    }


    @ModifyConstant(
            method = "writeNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            constant = @Constant(stringValue = "progress")
    )
    private String setLastProgress(String constant) {
        return CFSettings.pistonReloadInconsistencyFix ? "lastProgress" : constant;
    }


    @Inject(
            method = "writeNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;putFloat(Ljava/lang/String;F)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void setProgress(NbtCompound nbt, CallbackInfo ci) {
        if (CFSettings.pistonReloadInconsistencyFix) nbt.putFloat("progress", this.progress);
    }
}

package carpetfixes.mixins.brainFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.AllayBrain;
import net.minecraft.util.dynamic.GlobalPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.Optional;

@Mixin(AllayBrain.class)
public abstract class AllayBrain_noteblockMixin {

    @Shadow
    private static Optional<LookTarget> method_42662(LivingEntity livingEntity) {
        return Optional.empty();
    }


    @Inject(
            method = "method_42657(Lnet/minecraft/entity/LivingEntity;)Ljava/util/Optional;",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/AllayBrain;method_42658(" +
                            "Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/ai/brain/Brain;" +
                            "Lnet/minecraft/util/math/BlockPos;)Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private static void method_42657(LivingEntity entity, CallbackInfoReturnable<Optional<LookTarget>> cir,
                                     Brain<?> brain, Optional<GlobalPos> optionalGlobalPos) {
        if (CFSettings.allayWrongNoteblockFix &&
                !Objects.equals(entity.getWorld().getRegistryKey(),optionalGlobalPos.get().getDimension())) {
            brain.forget(MemoryModuleType.LIKED_NOTEBLOCK);
            cir.setReturnValue(method_42662(entity));
        }
    }
}

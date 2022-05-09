/*package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Bucketable.class)
public interface Bucketable_leashDetachMixin {

    // TODO: Figure out why this seems to crash when used outside of the IDE

    @Inject(
            method = "tryBucket",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;discard()V"
            )
    )
    private static <T extends LivingEntity & Bucketable> void tryBucket(PlayerEntity player, Hand hand, T entity, CallbackInfoReturnable<Optional<ActionResult>> cir) {
        if (CFSettings.bucketableMobsNotDetachingLeashesFix && entity instanceof MobEntity mobEntity)
            mobEntity.detachLeash(false,true);
    }
}*/

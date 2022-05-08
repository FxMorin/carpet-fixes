package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.LeashKnotDetach;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntity_leashDetachMixin {

    private final MobEntity self = (MobEntity)(Object)this;

    @Shadow
    private @Nullable Entity holdingEntity;


    @Inject(
            method = "detachLeash",
            at = @At("HEAD")
    )
    public void detachLeash(boolean sendPacket, boolean dropItem, CallbackInfo ci) {
        if (CFSettings.leashKnotNotUpdatingOnBreakFix && this.holdingEntity != null &&
                this.holdingEntity instanceof LeashKnotEntity leashKnotEntity)
            ((LeashKnotDetach)leashKnotEntity).onDetachLeash(self);
    }
}

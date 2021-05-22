package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_VisibilityCheckMixin extends Entity {
    public LivingEntity_VisibilityCheckMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "canSee",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onVisibilityCheck(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetFixesSettings.zombiePiglinTracingFix) {
            if (entity.world != this.world) {
                cir.setReturnValue(false);
            }
        }
    }
}

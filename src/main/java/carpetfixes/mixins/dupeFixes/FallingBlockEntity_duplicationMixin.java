package carpetfixes.mixins.dupeFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_duplicationMixin extends Entity {

    public FallingBlockEntity_duplicationMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/FallingBlockEntity;remove()V"), cancellable = true)
    private void cancelDupe(CallbackInfo ci) {
        if(CarpetFixesSettings.fallingBlockDuplicationFix && !this.isAlive()) {
            ci.cancel();
        }
    }
}

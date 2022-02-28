package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
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

    /**
     * Cancel the entity removal if the entity is already considered dead. This
     * prevents duplication of the falling block entity using portals.
     */


    public FallingBlockEntity_duplicationMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/FallingBlockEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"
            ),
            cancellable = true
    )
    public void cancelDupe(CallbackInfo ci) {
        if(CFSettings.fallingBlockDuplicationFix && this.isRemoved()) ci.cancel();
    }
}

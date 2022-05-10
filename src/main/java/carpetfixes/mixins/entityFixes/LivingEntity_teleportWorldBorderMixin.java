package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_teleportWorldBorderMixin extends Entity {

    public LivingEntity_teleportWorldBorderMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "teleport",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isChunkLoaded(Lnet/minecraft/util/math/BlockPos;)Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void cancelTeleport(double x, double y, double z, boolean pe, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.teleportPastWorldBorderFix && !this.world.getWorldBorder().contains(x, y, z))
            cir.setReturnValue(false);
    }
}

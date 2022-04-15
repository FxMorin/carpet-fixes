package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity_sneakMixin extends Entity {

    private double movementY;

    public PlayerEntity_sneakMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "adjustMovementForSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;method_30263()Z",
                    shift = At.Shift.AFTER
            )
    )
    protected void adjustMovementForSneaking(Vec3d movement, MovementType type, CallbackInfoReturnable<Vec3d> cir) {
        movementY = movement.y;
    }


    @Redirect(
            method = "adjustMovementForSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isSpaceEmpty(" +
                            "Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Z"
            )
    )
    protected boolean adjustMovementForSneaking(World instance, Entity entity, Box box) {
        return (!CFSettings.cantJumpOffBlockWhenSneakingFix || movementY <= 0) && instance.isSpaceEmpty(entity, box);
    }
}

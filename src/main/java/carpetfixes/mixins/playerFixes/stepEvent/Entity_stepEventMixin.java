package carpetfixes.mixins.playerFixes.stepEvent;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.patches.ServerPlayerEntityEmitStep;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class Entity_stepEventMixin {

    /**
     * ServerPlayerEntity overrides fall() and then calls super within ServerPlayNetworkHandler although
     * within ServerPlayNetworkHandler they call fall after move() which means that the STEP event will have
     * priority over the HIT_GROUND event because it was the first to happen within the tick.
     * To fix this, we prevent the STEP event from being emitted within move() and call it after the fall() call.
     */


    @Redirect(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;emitGameEvent(Lnet/minecraft/world/event/GameEvent;)V",
                    ordinal = 1
            )
    )
    public void cancelEmitGameEvent(Entity instance, GameEvent event) {
        if (CarpetFixesSettings.playerStepEventFix && instance instanceof ServerPlayerEntity) {
            ((ServerPlayerEntityEmitStep) instance).setShouldStep();
            return;
        }
        instance.emitGameEvent(event);
    }
}

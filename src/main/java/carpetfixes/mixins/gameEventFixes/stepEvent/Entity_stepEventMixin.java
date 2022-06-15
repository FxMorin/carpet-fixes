package carpetfixes.mixins.gameEventFixes.stepEvent;

import carpetfixes.CFSettings;
import carpetfixes.patches.ServerPlayerEntityEmitStep;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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

    private final Entity self = (Entity)(Object)this;


    @Redirect(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/world/event/GameEvent;" +
                            "Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/world/event/GameEvent$Emitter;)V"
            )
    )
    public void cancelEmitGameEvent(World instance, GameEvent gameEvent, Vec3d vec3d, GameEvent.Emitter emitter) {
        if (CFSettings.playerStepEventFix && self instanceof ServerPlayerEntity) {
            ((ServerPlayerEntityEmitStep) self).setShouldStep();
        } else {
            instance.emitGameEvent(gameEvent, vec3d, emitter);
        }
    }
}

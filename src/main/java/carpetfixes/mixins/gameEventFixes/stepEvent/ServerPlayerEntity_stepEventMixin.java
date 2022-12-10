package carpetfixes.mixins.gameEventFixes.stepEvent;

import carpetfixes.patches.ServerPlayerEntityEmitStep;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

/**
 * This class implements ServerPlayerEntityEmitStep to keep track of the shouldStep state in serverPlayerEntity
 */

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntity_stepEventMixin implements ServerPlayerEntityEmitStep {

    private boolean shouldStep = false;

    @Override
    public boolean shouldStep() {
        boolean ss = shouldStep;
        shouldStep = false;
        return ss;
    }

    @Override
    public void setShouldStep() {
        this.shouldStep = true;
    }
}

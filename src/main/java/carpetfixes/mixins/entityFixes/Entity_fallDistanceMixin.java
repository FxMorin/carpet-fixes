package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class Entity_fallDistanceMixin {

    @Shadow public float fallDistance;
    @Shadow public World world;
    @Shadow public abstract void emitGameEvent(GameEvent event);


    @Inject(
            method = "fall",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition, CallbackInfo ci) {
        if (CarpetFixesSettings.incorrectFallDamageFix) {
            if (onGround) {
                if (this.fallDistance > 0.0F) {
                    landedState.getBlock().onLandedUpon(this.world, landedState, landedPosition, (Entity) (Object) this, this.fallDistance);
                    if (!landedState.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                        this.emitGameEvent(GameEvent.HIT_GROUND);
                    }
                }
                this.fallDistance = 0.0F;
            } else if (heightDifference < 0.0D) {
                this.fallDistance = (float) ((double) this.fallDistance-heightDifference);
            } else if (heightDifference > 0.0D) { //Add back in the heightDifference if going upwards
                this.fallDistance = Math.max((float)((double) this.fallDistance-heightDifference),0);
            }
            ci.cancel();
        }
    }
}

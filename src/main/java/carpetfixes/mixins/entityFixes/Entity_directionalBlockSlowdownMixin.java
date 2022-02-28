package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class Entity_directionalBlockSlowdownMixin {

    /**
     * A very simple and nice fix, basically if the multiplier is not 0 (default)
     * then we know there was a block that set the slowdown before us. So we check
     * which block is the slowest and use that for the multiplier. Very simple and nice
     */


    @Shadow
    public float fallDistance;

    @Shadow
    protected Vec3d movementMultiplier;


    @Inject(
            method = "slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void slowMovement(BlockState state, Vec3d m, CallbackInfo ci) {
        if (CFSettings.directionalBlockSlowdownFix) {
            this.fallDistance = 0.0F;
            if (this.movementMultiplier.length() > 0.0) {
                this.movementMultiplier = new Vec3d(
                        this.movementMultiplier.x > 0 ? Math.min(this.movementMultiplier.x, m.x) : m.x,
                        this.movementMultiplier.y > 0 ? Math.min(this.movementMultiplier.y, m.y) : m.y,
                        this.movementMultiplier.z > 0 ? Math.min(this.movementMultiplier.z, m.z) : m.z);
            } else {
                this.movementMultiplier = m;
            }
            ci.cancel();
        }
    }
}

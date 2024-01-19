package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Adds a tiny bit of upwards velocity to tnt blocks, enough so that they don't fall through fences when lit
 */

@Mixin(TntBlock.class)
public class TntBlock_extendedCollisionMixin {

    // TODO: Add collision check before adding more velocity. Although it's more expensive...


    @Redirect(
            method = "primeTnt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private static boolean cf$adjustVelocityBeforeSpawn(World world, Entity entity) {
        if (CFSettings.tntExtendedHitboxClipFix) {
            Vec3d velocity = entity.getVelocity();
            entity.setVelocity(velocity.x,0.22649273522,velocity.z); // I personally don't like this fix much
        }
        return world.spawnEntity(entity);
    }
}

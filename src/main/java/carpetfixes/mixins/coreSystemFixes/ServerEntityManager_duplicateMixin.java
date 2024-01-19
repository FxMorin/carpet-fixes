package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import carpetfixes.CarpetFixesServer;
import carpetfixes.mixins.accessors.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;
import java.util.UUID;

/**
 * Fix multiple entities having the same UUID, causing conflicts
 */

@Mixin(ServerEntityManager.class)
public class ServerEntityManager_duplicateMixin<T extends EntityLike> {

    @Shadow
    private boolean addEntityUuid(T entity) {
        return true;
    }

    @Shadow
    @Final
    Set<UUID> entityUuids;


    @Redirect(
            method = "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerEntityManager;" +
                            "addEntityUuid(Lnet/minecraft/world/entity/EntityLike;)Z"
            )
    )
    private boolean cf$addEntityUuidOrSwitchUuid(ServerEntityManager<T> manager, T entity) {
        if (CFSettings.duplicateEntityUUIDFix) {
            if (!this.entityUuids.add(entity.getUuid()) && entity instanceof Entity e) {
                int attempt = 0;
                do {
                    UUID newUuid = MathHelper.randomUuid(((EntityAccessor) e).getRandom());
                    if (this.entityUuids.add(newUuid)) {
                        ((EntityAccessor) e).setUuid(newUuid);
                        CarpetFixesServer.LOGGER.info("Fixed duplicate uuid for: {}", entity);
                        return true;
                    }
                } while (attempt++ < 3); //Collision protection (near impossible)
            }
            return true;
        }
        return this.addEntityUuid(entity);
    }
}

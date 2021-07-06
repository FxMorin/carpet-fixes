package carpetfixes.mixins.conversionFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VillagerEntity.class)
public class VillagerEntity_conversionMixin {

    private final VillagerEntity self = (VillagerEntity)(Object)this;

    /*@Redirect(method= "onStruckByLightning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LightningEntity;)V",at=@At(value="INVOKE",target="Lnet/minecraft/world/ServerWorld;spawnEntityAndPassengers(Lnet/minecraft/entity/Entity;)V"))
    public void spawnEntityAndPassengers(ServerWorld serverWorld, Entity entity) {
        if (CarpetFixesSettings.conversionFix) {
            entity.setFireTicks(self.getFireTicks()); //Fire
            entity.setVelocity(self.getVelocity()); //Motion
            entity.setNoGravity(self.hasNoGravity()); //noGravity
            entity.setSilent(self.isSilent()); //Silent
            ((MobEntity)entity).setLeftHanded(self.isLeftHanded()); //Left Handed
            serverWorld.spawnEntityAndPassengers(entity);
            entity.resetPosition();
            entity.tick();
            if (!serverWorld.isClient) {
                ((ServerWorld) entity.getEntityWorld()).getChunkManager().sendToNearbyPlayers(entity, new EntityPositionS2CPacket(entity));
            }
        } else {
            serverWorld.spawnEntityAndPassengers(entity);
        }
    }*/
}

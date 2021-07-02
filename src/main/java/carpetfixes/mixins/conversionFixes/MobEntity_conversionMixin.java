package carpetfixes.mixins.conversionFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntity.class)
public abstract class MobEntity_conversionMixin extends LivingEntity  {
    protected MobEntity_conversionMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

    @Redirect(method = "convertTo(Lnet/minecraft/entity/EntityType;Z)Lnet/minecraft/entity/mob/MobEntity;", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            ordinal = 0
    ))
    public boolean ConversionFix(World world, Entity entity) {
        if (CarpetFixesSettings.conversionFix) {
            entity.setFireTicks(this.getFireTicks()); //Fire
            entity.setVelocity(this.getVelocity()); //Motion
            entity.copyPositionAndRotation(this); //Rotation
            entity.setNoGravity(this.hasNoGravity()); //noGravity
            entity.setSilent(this.isSilent()); //Silent
            ((MobEntity)entity).setLeftHanded(((MobEntity)(Object)this).isLeftHanded()); //Left Handed
            boolean didWork = world.spawnEntity(entity);
            entity.resetPosition();
            entity.tick();
            if (!world.isClient) {
                ((ServerWorld) entity.getEntityWorld()).getChunkManager().sendToNearbyPlayers(entity, new EntityPositionS2CPacket(entity));
            }
            return didWork;
        }
        return world.spawnEntity(entity);
    }
}

package carpetfixes.mixins.conversionFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntity_conversionMixin extends MobEntity implements Monster {

    protected SlimeEntity_conversionMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    //Since slime is not a full conversion and instead splits into multiple entities
    //PortalCooldown, Rotation, effects, & Health are ignored
    //DeathLootTable & tags still need to be implemented!

    @Redirect(method = "remove", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            ordinal = 0
    ))
    public boolean ConversionFixSlime(World world, Entity slimeEntity) {
        if (CarpetFixesSettings.conversionFix) {
            slimeEntity.setFireTicks(this.getFireTicks()); //Fire
            slimeEntity.setVelocity(this.getVelocity()); //Motion
            slimeEntity.setNoGravity(this.hasNoGravity()); //noGravity
            slimeEntity.setSilent(this.isSilent()); //Silent
            ((MobEntity)slimeEntity).setLeftHanded(this.isLeftHanded()); //Left Handed
            boolean didWork = this.world.spawnEntity(slimeEntity);
            slimeEntity.resetPosition();
            slimeEntity.tick();
            if (!world.isClient) {
                ((ServerWorld) slimeEntity.getEntityWorld()).getChunkManager().sendToNearbyPlayers(slimeEntity, new EntityPositionS2CPacket(slimeEntity));
            }
            return didWork;
        }
        return this.world.spawnEntity(slimeEntity);
    }
}
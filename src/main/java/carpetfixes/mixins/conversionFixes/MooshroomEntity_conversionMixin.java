package carpetfixes.mixins.conversionFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MooshroomEntity.class)
public abstract class MooshroomEntity_conversionMixin {

    private final CowEntity self = (CowEntity)(Object)this;

    @Redirect(method = "sheared(Lnet/minecraft/sound/SoundCategory;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            ordinal = 0
    ))
    public boolean ConversionFix(World world, Entity cowEntity) {
        if (CarpetFixesSettings.conversionFix && cowEntity instanceof CowEntity) {
            cowEntity.setVelocity(self.getVelocity()); //Motion
            cowEntity.setNoGravity(self.hasNoGravity()); //noGravity
            cowEntity.setFireTicks(self.getFireTicks()); //Fire
            cowEntity.setSilent(self.isSilent()); //Silent
            ((MobEntity)cowEntity).setLeftHanded(self.isLeftHanded()); //Left Handed
            //slimeEntity.setAir(this.getAir()); //Air
        }
        return world.spawnEntity(cowEntity);
    }
}


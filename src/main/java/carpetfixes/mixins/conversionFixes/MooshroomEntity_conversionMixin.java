package carpetfixes.mixins.conversionFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mooshroom convert into cows when sheared. When they convert, they do not transfer all the correct data to the
 * new entity. The fix is simply to transfer the missing information over to the new entity.
 */

@Mixin(MooshroomEntity.class)
public abstract class MooshroomEntity_conversionMixin {


    @Redirect(
            method = "sheared(Lnet/minecraft/sound/SoundCategory;)V",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
                ordinal = 0
            )
    )
    private boolean cf$conversionFix(World world, Entity cowEntity) {
        if (CFSettings.conversionFix && cowEntity instanceof CowEntity) {
            CowEntity self = (CowEntity)(Object)this;
            cowEntity.setVelocity(self.getVelocity()); //Motion
            cowEntity.setNoGravity(self.hasNoGravity()); //noGravity
            cowEntity.setFireTicks(self.getFireTicks()); //Fire
            cowEntity.setSilent(self.isSilent()); //Silent
            ((MobEntity)cowEntity).setLeftHanded(self.isLeftHanded()); //Left Handed
            //cowEntity.setAir(this.getAir()); //Air
        }
        return world.spawnEntity(cowEntity);
    }
}


package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes other zombie variants not spawning the correct variants for the reinforcements
 */

@Mixin(ZombieEntity.class)
public class ZombieEntity_reinforcementTypeMixin extends HostileEntity {

    protected ZombieEntity_reinforcementTypeMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Redirect(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/entity/mob/ZombieEntity",
                    ordinal = 0
            )
    )
    private ZombieEntity modifyType(World instance) {
        return CFSettings.reinforcementsOnlySpawnZombiesFix ?
                (ZombieEntity)this.getType().create(this.world) :
                new ZombieEntity(this.world);
    }
}

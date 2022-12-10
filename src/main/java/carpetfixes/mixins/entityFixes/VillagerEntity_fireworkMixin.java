package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

/**
 * Fixes villagers getting killed by their own fireworks
 */

@Mixin(VillagerEntity.class)
public abstract class VillagerEntity_fireworkMixin extends LivingEntity {

    protected VillagerEntity_fireworkMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        if (CFSettings.badVillagerPyrotechnicsFix && source.isProjectile() && source.isExplosive() &&
                Objects.equals(source.getName(), "fireworks") && source.getAttacker() instanceof VillagerEntity)
            return false;
        return super.damage(source, amount);
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntity_markerMixin extends LivingEntity {

    @Shadow
    public abstract boolean isMarker();

    protected ArmorStandEntity_markerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public boolean canAvoidTraps() {
        return super.canAvoidTraps() || (CFSettings.markerArmorStandsTriggerBlocksFix && this.isMarker());
    }

    @Override
    public boolean canBreatheInWater() {
        return super.canBreatheInWater() || (CFSettings.markerArmorStandsCreateBubblesFix && this.isMarker());
    }
}

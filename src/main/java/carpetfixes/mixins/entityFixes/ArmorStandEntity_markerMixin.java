package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntity_markerMixin extends Entity {

    @Shadow public abstract boolean isMarker();

    public ArmorStandEntity_markerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean canAvoidTraps() {
        return CFSettings.markerArmorstandsTriggerBlocksFix && this.isMarker();
    }
}

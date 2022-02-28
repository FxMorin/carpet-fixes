package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntity_aggroMechanicsMixin extends LivingEntity {

    @Shadow
    public abstract boolean isMarker();

    protected ArmorStandEntity_aggroMechanicsMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //Use the marker entity if you want something which acts exactly like an armor stand set to isMarker or isInvisible
    //Also, please stop making "optimizations" which removed behaviour or limit what's possible
    //At least make the armorstand able to bypass these optimizations for data packs

    @Override
    public boolean canTarget(LivingEntity target) {
        return CFSettings.armorStandMissingFunctionalityFix || super.canTarget(target);
    }

    @Override
    public boolean isPartOfGame() {
        return (CFSettings.armorStandMissingFunctionalityFix && !this.isMarker()) ||
                (!this.isInvisible()) && !this.isMarker();
    }
}

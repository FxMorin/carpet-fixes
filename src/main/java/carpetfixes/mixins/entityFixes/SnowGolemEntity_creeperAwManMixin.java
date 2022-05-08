package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntity_creeperAwManMixin extends GolemEntity {

    protected SnowGolemEntity_creeperAwManMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public boolean canTarget(EntityType<?> type) {
        return (!CFSettings.snowGolemAttackCreepersFix || type != EntityType.CREEPER) && super.canTarget(type);
    }
}

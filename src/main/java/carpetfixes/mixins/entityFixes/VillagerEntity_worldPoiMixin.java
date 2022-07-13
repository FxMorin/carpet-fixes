package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntity_worldPoiMixin extends MerchantEntity {

    public VillagerEntity_worldPoiMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract void releaseAllTickets();


    @Shadow public abstract Brain<VillagerEntity> getBrain();

    @Shadow public abstract void setVillagerData(VillagerData villagerData);

    @Shadow public abstract VillagerData getVillagerData();

    @Shadow public abstract void reinitializeBrain(ServerWorld world);

    @Override
    public Entity moveToWorld(ServerWorld destination) {
        if (CFSettings.villagersDontReleaseMemoryFix) {
            this.releaseAllTickets();
            this.getBrain().forget(MemoryModuleType.HOME);
            this.getBrain().forget(MemoryModuleType.JOB_SITE);
            this.getBrain().forget(MemoryModuleType.POTENTIAL_JOB_SITE);
            this.getBrain().forget(MemoryModuleType.MEETING_POINT);
            this.reinitializeBrain(destination);
        }
        return super.moveToWorld(destination);
    }
}

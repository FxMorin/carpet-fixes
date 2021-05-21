package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ItemEntity_lightningKillsDropsMixin extends Entity {
    @Shadow private int age;

    public ItemEntity_lightningKillsDropsMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (CarpetFixesSettings.lightningKillsDropsFix) {
            if (this.age > 8) { //Only kill item if its older then 8 ticks
                super.onStruckByLightning(world, lightning);
            }
        } else {
            super.onStruckByLightning(world, lightning);
        }
    }
}

package carpetfixes.mixins.itemFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Here we add a onStruckByLightning condition, so that the lightning bolt entity does not destroy items that where
 * created by the entities it created since the lightning bolt can live for 8 ticks, we make sure to avoid all
 * item entities that have been alive for less than 9 ticks
 */

@Mixin(ItemEntity.class)
public abstract class ItemEntity_lightningKillsDropsMixin extends Entity {

    @Shadow
    private int itemAge;

    public ItemEntity_lightningKillsDropsMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (CFSettings.lightningKillsDropsFix) {
            if (this.itemAge > 8) {
                super.onStruckByLightning(world, lightning);
            }
        } else {
            super.onStruckByLightning(world, lightning);
        }
    }
}

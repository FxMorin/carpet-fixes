package carpetfixes.mixins.entityFixes;

import carpetfixes.mixins.accessors.MobEntityAccessor;
import carpetfixes.patches.LeashKnotDetach;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/**
 * Sets a method for the LeashKnotDetach implementation to use.
 * Basically just checks for entities like the leash knot would usually do, and kill itself if its not
 * connected to an entity
 */

@Mixin(LeashKnotEntity.class)
public abstract class LeashKnotEntity_detachMixin extends Entity implements LeashKnotDetach {

    public LeashKnotEntity_detachMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private final LeashKnotEntity self = (LeashKnotEntity)(Object)this;


    @Override
    public void onDetachLeash(MobEntity caller) {
        double d = 7.0;
        List<MobEntity> list = this.getWorld()
                .getNonSpectatingEntities(
                        MobEntity.class, new Box(this.getX() - d, this.getY() - d, this.getZ() - d, this.getX() + d, this.getY() + d, this.getZ() + d)
                );
        boolean shouldDestroy = true;
        for(MobEntity mobEntity : list) {
            if (mobEntity == caller) continue;
            Entity holdingEntity = ((MobEntityAccessor)mobEntity).getHoldingEntity();
            if (holdingEntity != null && holdingEntity == self) {
                shouldDestroy = false;
                break;
            }
        }
        if (shouldDestroy) self.kill();
    }
}

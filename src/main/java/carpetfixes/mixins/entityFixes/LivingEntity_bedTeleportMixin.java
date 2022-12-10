package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Prevents you from being able to teleport while sleeping, fixing two 8x coord multiplication exploits
 */

@Mixin(LivingEntity.class)
public abstract class LivingEntity_bedTeleportMixin extends Entity {

    @Shadow
    public abstract boolean isSleeping();

    public LivingEntity_bedTeleportMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Override
    protected void tickPortal() {
        if (CFSettings.bedTeleportExploitFix && this.isSleeping()) return;
        super.tickPortal();
    }
}

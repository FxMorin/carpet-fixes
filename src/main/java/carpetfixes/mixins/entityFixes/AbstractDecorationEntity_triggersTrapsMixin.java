package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Fixes decoration entities being able to trigger traps (such as tripwire)
 */

@Mixin(AbstractDecorationEntity.class)
public abstract class AbstractDecorationEntity_triggersTrapsMixin extends Entity {

    public AbstractDecorationEntity_triggersTrapsMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Override
    public boolean canAvoidTraps() {
        return CFSettings.hangingEntityTriggersTrapsFix;
    }
}

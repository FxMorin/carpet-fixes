package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Fixes being able to dispense armor onto the warden, and it just disappears. Obv because the warden ate it
 */

@Mixin(WardenEntity.class)
public abstract class WardenEntity_eatsArmorMixin extends LivingEntity {

    protected WardenEntity_eatsArmorMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        return !CFSettings.wardenEatsArmorFix && super.canEquip(stack);
    }
}

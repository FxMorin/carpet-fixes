package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AllayEntity.class)
public abstract class AllayEntity_itemPickupMixin extends MobEntity {

    protected AllayEntity_itemPickupMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public boolean canEquip(ItemStack stack) {
        return !CFSettings.allayCanBeEquippedWithArmorFix && super.canEquip(stack);
    }
}

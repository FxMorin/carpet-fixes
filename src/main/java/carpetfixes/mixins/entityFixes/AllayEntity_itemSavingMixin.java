package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AllayEntity.class)
public abstract class AllayEntity_itemSavingMixin extends MobEntity {

    @Shadow
    @Final
    private SimpleInventory inventory;

    protected AllayEntity_itemSavingMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (CFSettings.allayLosesItemOnUnloadFix) {
            nbt.put("CarriedStack", this.inventory.toNbtList());
        }
    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (CFSettings.allayLosesItemOnUnloadFix) {
            this.inventory.readNbtList(nbt.getList("CarriedStack",10));
        }
    }
}

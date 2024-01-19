package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Fixes blue wither skulls not being saved, and instead converted to normal wither skulls when reloading the chunks
 */

@Mixin(WitherSkullEntity.class)
public abstract class WitherSkullEntity_chargedMixin extends ExplosiveProjectileEntity {

    @Shadow
    public abstract boolean isCharged();

    @Shadow
    public abstract void setCharged(boolean charged);

    protected WitherSkullEntity_chargedMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (CFSettings.blueWitherSkullNotSavedFix && this.isCharged()) {
            nbt.putBoolean("charged", true);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (CFSettings.blueWitherSkullNotSavedFix) {
            this.setCharged(nbt.getBoolean("charged"));
        }
    }
}

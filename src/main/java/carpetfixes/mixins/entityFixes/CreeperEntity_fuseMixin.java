package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntity_fuseMixin extends HostileEntity {


    @Shadow private int currentFuseTime;
    @Shadow private int lastFuseTime;


    protected CreeperEntity_fuseMixin(EntityType<? extends HostileEntity> entityType, World world) {super(entityType, world);}


    @Override
    public void copyFrom(Entity original) {
        NbtCompound nbtCompound = original.writeNbt(new NbtCompound());
        nbtCompound.remove("Dimension");
        this.readNbt(nbtCompound);
        this.netherPortalCooldown = original.netherPortalCooldown;
        this.lastNetherPortalPosition = original.lastNetherPortalPosition;
        if (CarpetFixesSettings.creeperPortalFuseResetsFix) {
            CreeperEntity self = (CreeperEntity) original;
            this.lastFuseTime = self.lastFuseTime;
            this.currentFuseTime = self.currentFuseTime;
        }
    }
}

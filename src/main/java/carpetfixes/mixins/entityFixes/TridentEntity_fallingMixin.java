package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TridentEntity.class)
public abstract class TridentEntity_fallingMixin extends PersistentProjectileEntity {

    protected TridentEntity_fallingMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    private boolean dealtDamage;


    @Override
    protected void fall() {
        super.fall();
        if (CFSettings.tridentFallingDamageFix) {
            this.dealtDamage = false;
            this.inGroundTime = 0;
        }
    }
}

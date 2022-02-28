package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TridentEntity.class)
public abstract class TridentEntity_voidLoyaltyMixin extends Entity {

    @Shadow private boolean dealtDamage;
    @Shadow @Final private static TrackedData<Byte> LOYALTY;

    public TridentEntity_voidLoyaltyMixin(EntityType<?> type, World world) {super(type, world);}

    @Override
    public void attemptTickInVoid() {
        if (this.getY() < (double) (this.world.getBottomY() - 64)) {
            if (CFSettings.voidKillsLoyaltyTridentsFix && this.dataTracker.get(LOYALTY) > 0) {
                this.dealtDamage = true;
                if (this.getY() < (double) (this.world.getBottomY() - 128)) {
                    this.tickInVoid();
                }
            } else {
                this.tickInVoid();
            }
        }
    }
}

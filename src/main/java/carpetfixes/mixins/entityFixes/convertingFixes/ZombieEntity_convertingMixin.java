package carpetfixes.mixins.entityFixes.convertingFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntity_convertingMixin extends HostileEntity {

    @Shadow
    protected abstract boolean canConvertInWater();

    protected ZombieEntity_convertingMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "isConvertingInWater()Z",
            at = @At("RETURN"),
            cancellable = true
    )
    public void isConverting(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.mobsConvertingWithoutBlocksFix) {
            cir.setReturnValue(
                    cir.getReturnValue() && this.canConvertInWater() && this.isSubmergedIn(FluidTags.WATER)
            );
        }
    }
}

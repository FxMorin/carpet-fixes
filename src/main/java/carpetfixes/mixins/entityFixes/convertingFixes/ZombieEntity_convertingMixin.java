package carpetfixes.mixins.entityFixes.convertingFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
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

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.GT_22w05a))
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

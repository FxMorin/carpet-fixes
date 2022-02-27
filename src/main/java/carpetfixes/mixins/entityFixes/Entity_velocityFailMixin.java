package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.LT_1_18_2_pre1))
@Mixin(Entity.class)
public abstract class Entity_velocityFailMixin {

    @Shadow public abstract Vec3d getVelocity();

    @Shadow public abstract void setVelocity(double x, double y, double z);


    @Redirect(
            method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/Entity;fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setVelocity(DDD)V",
                    ordinal = 1
            )
    )
    private void dontResetX(Entity instance, double x, double y, double z) {
        if (CarpetFixesSettings.velocityNotCancelledFix) this.setVelocity(this.getVelocity().x, y, z);
    }
}

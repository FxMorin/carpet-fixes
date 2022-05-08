package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntMinecartEntity.class)
public abstract class TntMinecartEntity_horizontalCollisionMixin extends AbstractMinecartEntity {

    protected TntMinecartEntity_horizontalCollisionMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    // This is kinda expensive, a better solution would be to cache a boolean to specify if the minecart is on rails
    // in the AbstractMinecartEntity tick(), then reusing that here instead


    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/TntMinecartEntity;explode(D)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void tick(CallbackInfo ci) {
        if (CFSettings.tntMinecartTerribleCollisionFix &&
                AbstractRailBlock.isRail(this.world.getBlockState(this.getBlockPos()))) ci.cancel();
    }
}

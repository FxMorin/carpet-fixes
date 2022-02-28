package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntMinecartEntity.class)
public abstract class TntMinecartEntity_doubleExplodeMixin extends Entity {


    public TntMinecartEntity_doubleExplodeMixin(EntityType<?> type, World world) {super(type, world);}


    @Inject(
            method= "tick()V",
            at= @At(
                    shift= At.Shift.AFTER,
                    value= "INVOKE",
                    target="Lnet/minecraft/entity/vehicle/TntMinecartEntity;explode(D)V"
            ),
            cancellable = true
    )
    private void cancelOnceDoneExplode(CallbackInfo ci) {
        if (CFSettings.tntMinecartExplodesTwiceFix && this.isRemoved()) ci.cancel();
    }
}

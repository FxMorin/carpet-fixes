package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_sleepingKillsMixin extends Entity {

    /**
     * If you fall from a high place into you bed, once you wake up you die from fall damage.
     * When velocity gets set to ZERO when you sleep, we should also be setting the fallDistance
     * to 0, to ensure that no weird behaviour happens.
     */


    public LivingEntity_sleepingKillsMixin(EntityType<?> type, World world) { super(type, world); }


    @Inject(
            method= "setPositionInBed(Lnet/minecraft/util/math/BlockPos;)V",
            at=@At("HEAD")
    )
    private void saferSleep(BlockPos pos, CallbackInfo ci) {
        if (CarpetFixesSettings.sleepingDelaysFallDamageFix) this.fallDistance = 0.0F;
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * When a villager in a bed gets struck by lightning, the villager gets converted to a witch. The bed however does not
 * get set to unoccupied
 */

@Mixin(VillagerEntity.class)
public abstract class VillagerEntity_witchBedMixin extends MerchantEntity {

    public VillagerEntity_witchBedMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract void wakeUp();


    @Inject(
            method = "onStruckByLightning(Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/entity/LightningEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType;" +
                            "create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;",
                    shift = At.Shift.BEFORE
            )
    )
    private void cf$onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        //Sets bed occupied to false
        //As a side note, this also places the villager in a valid spot, right before the witch gets created
        //with the villagers position. So the witch will be placed correctly, which is an unreported bug!
        if (CFSettings.villagerToWitchBedOccupiedFix && this.isSleeping()) {
            this.wakeUp();
        }
    }
}

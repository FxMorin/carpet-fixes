package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes foxes dropping loot even though the do mob loot gamerule is set to false
 */

@Mixin(FoxEntity.class)
public abstract class FoxEntity_lootDropMixin extends AnimalEntity {

    protected FoxEntity_lootDropMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "drop(Lnet/minecraft/entity/damage/DamageSource;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$onDrop(DamageSource source, CallbackInfo ci) {
        if (CFSettings.foxesDropItemsWithLootOffFix &&
                !this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            ci.cancel();
        }
    }
}

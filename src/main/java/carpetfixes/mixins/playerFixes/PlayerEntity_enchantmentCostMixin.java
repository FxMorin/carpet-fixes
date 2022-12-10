package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes enchanting in creative mode still deducting experience
 */

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity_enchantmentCostMixin extends Entity {

    public PlayerEntity_enchantmentCostMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean isCreative();

    @Shadow
    protected int enchantmentTableSeed;


    @Inject(
            method = "applyEnchantmentCosts",
            at = @At("HEAD"),
            cancellable = true
    )
    private void applyEnchantmentCosts(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci) {
        if (CFSettings.creativeEnchantingCostsExperienceFix && this.isCreative()) {
            this.enchantmentTableSeed = this.random.nextInt();
            ci.cancel();
        }
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

/**
 * The trident wielding Drowned, do not actually use the enchantments that are on the tridents.
 * So here we check if they are holding an enchanted trident, and if so apply the enchantments.
 */

@Mixin(DrownedEntity.class)
public class DrownedEntity_enchantedTridentMixin extends ZombieEntity {


    public DrownedEntity_enchantedTridentMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }


    @Redirect(
            method = "shootAt",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/item/ItemStack"
            )
    )
    private ItemStack createItemStack(ItemConvertible item) {
        ItemStack trident = new ItemStack(item);
        if (CFSettings.drownedEnchantedTridentsFix) {
            ItemStack holding = this.getActiveItem();
            if (holding.getItem() == Items.TRIDENT) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(holding);
                enchantments.remove(Enchantments.LOYALTY);
                EnchantmentHelper.set(enchantments, trident);
            }
        }
        return trident;
    }
}

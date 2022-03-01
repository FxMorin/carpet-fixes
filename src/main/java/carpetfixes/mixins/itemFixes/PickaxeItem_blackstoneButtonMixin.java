package carpetfixes.mixins.itemFixes;

import carpet.CarpetSettings;
import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.GT_22w05a))
@Mixin(value=PickaxeItem.class,priority=1010)
public class PickaxeItem_blackstoneButtonMixin extends MiningToolItem {

    //TODO: Might be broken by carpet now

    protected PickaxeItem_blackstoneButtonMixin(float attackDamage, float attackSpeed, ToolMaterial material,
                                                TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return ((CFSettings.blackstoneButtonBreakSpeedFix && state.isOf(Blocks.POLISHED_BLACKSTONE_BUTTON)) ||
                (CarpetSettings.missingTools && state.getMaterial() == Material.GLASS)) ?
                miningSpeed :
                super.getMiningSpeedMultiplier(stack, state);
    }
}

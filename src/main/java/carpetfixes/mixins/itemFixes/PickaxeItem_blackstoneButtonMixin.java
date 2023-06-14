package carpetfixes.mixins.itemFixes;

import carpet.CarpetSettings;
import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Fixes blackstone buttons taking longer to mine due to them not having a tool assigned to them
 */

@Mixin(value = PickaxeItem.class, priority = 1010)
public class PickaxeItem_blackstoneButtonMixin extends MiningToolItem {

    //TODO: Might be broken by carpet now

    protected PickaxeItem_blackstoneButtonMixin(float attackDamage, float attackSpeed, ToolMaterial material,
                                                TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return ((CFSettings.blackstoneButtonBreakSpeedFix && state.isOf(Blocks.POLISHED_BLACKSTONE_BUTTON)) ||
                (CarpetSettings.missingTools && state.getBlock().getSoundGroup(state) == BlockSoundGroup.GLASS)) ?
                miningSpeed :
                super.getMiningSpeedMultiplier(stack, state);
    }
}

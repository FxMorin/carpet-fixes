package carpetfixes.mixins.itemFixes;

import carpet.CarpetSettings;
import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value=PickaxeItem.class,priority=1010)
public class PickaxeItem_blackstoneButtonMixin extends MiningToolItem {

    protected PickaxeItem_blackstoneButtonMixin(float attackDamage, float attackSpeed, ToolMaterial material, Tag<Block> effectiveBlocks, Settings settings) {super(attackDamage, attackSpeed, material, effectiveBlocks, settings);}

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return ((CFSettings.blackstoneButtonBreakSpeedFix && state.isOf(Blocks.POLISHED_BLACKSTONE_BUTTON)) || (CarpetSettings.missingTools && state.getMaterial() == Material.GLASS)) ? miningSpeed : super.getMiningSpeedMultiplier(stack, state);
    }
}

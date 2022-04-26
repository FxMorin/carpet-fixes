package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.GT_22w06a))
@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlock_transparentMixin {


    @Redirect(
            method = "canAccessBookshelf(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isAir(Lnet/minecraft/util/math/BlockPos;)Z"
            ),
            require = 0
    )
    private static boolean isTranslucent(World world, BlockPos pos) {
        return CFSettings.transparentBlocksNegateEnchantingFix ?
                !world.getBlockState(pos).isFullCube(world,pos) : world.isAir(pos);
    }
}

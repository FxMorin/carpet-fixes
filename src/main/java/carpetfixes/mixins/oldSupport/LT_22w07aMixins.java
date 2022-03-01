package carpetfixes.mixins.oldSupport;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.LT_22w07a))
@Mixin(EnchantmentScreenHandler.class)
class old_EnchantmentScreenHandler_transparentBlocksMixin {


    @SuppressWarnings("all")
    @Redirect(
            method = "method_17411(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;)V",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isAir(Lnet/minecraft/util/math/BlockPos;)Z"
            )
    )
    public boolean isTranslucent(World world, BlockPos pos) {
        return CFSettings.transparentBlocksNegateEnchantingFix ?
                !world.getBlockState(pos).isFullCube(world, pos) :
                world.isAir(pos);
    }
}

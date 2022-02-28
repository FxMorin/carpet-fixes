package carpetfixes.mixins.screenHandlerFixes;

import carpetfixes.CFSettings;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandler_transparentBlocksMixin {


    @SuppressWarnings("target")
    @Redirect(
            method = "method_17411(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isAir(Lnet/minecraft/util/math/BlockPos;)Z"
            )
    )
    public boolean isTranslucent(World world, BlockPos pos) {
        return CFSettings.transparentBlocksNegateEnchantingFix ?
                !world.getBlockState(pos).isFullCube(world,pos) :
                world.isAir(pos);
    }
}

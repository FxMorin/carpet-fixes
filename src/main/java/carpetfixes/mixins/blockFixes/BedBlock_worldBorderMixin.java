package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BedBlock.class)
public class BedBlock_worldBorderMixin {


    @Redirect(
            method= "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/block/BlockState;canReplace(Lnet/minecraft/item/ItemPlacementContext;)Z"
            ))
    public boolean getPlacementState(BlockState blockState, ItemPlacementContext context) {
        return (!CarpetFixesSettings.bedsCanBeInWorldBorderFix || context.getWorld().getWorldBorder().contains(context.getBlockPos().offset(context.getPlayerFacing()))) && blockState.canReplace(context);
    }
}

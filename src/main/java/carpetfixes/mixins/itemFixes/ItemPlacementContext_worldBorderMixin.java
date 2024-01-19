package carpetfixes.mixins.itemFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes the ability to place blocks outside the world border
 */

@Mixin(ItemPlacementContext.class)
public abstract class ItemPlacementContext_worldBorderMixin extends ItemUsageContext {

    @Shadow
    protected boolean canReplaceExisting;

    public ItemPlacementContext_worldBorderMixin(PlayerEntity player, Hand hand, BlockHitResult hit) {
        super(player, hand, hit);
    }


    @Inject(
            method = "canPlace",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$canPlace(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.placeBlocksOutsideWorldBorderFix) {
            cir.setReturnValue(
                    this.getWorld().getWorldBorder().contains(this.getBlockPos()) && (
                                    this.canReplaceExisting ||
                                    this.getWorld().getBlockState(this.getBlockPos())
                                            .canReplace((ItemPlacementContext) (Object) this)
                    )
            );
        }
    }
}

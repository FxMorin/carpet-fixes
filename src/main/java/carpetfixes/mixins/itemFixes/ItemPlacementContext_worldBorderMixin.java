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

@Mixin(ItemPlacementContext.class)
public abstract class ItemPlacementContext_worldBorderMixin extends ItemUsageContext {

    @Shadow
    protected boolean canReplaceExisting;

    private final ItemPlacementContext self = (ItemPlacementContext) (Object) this;

    public ItemPlacementContext_worldBorderMixin(PlayerEntity player, Hand hand, BlockHitResult hit) {
        super(player, hand, hit);
    }


    @Inject(
            method = "canPlace",
            at = @At("HEAD"),
            cancellable = true
    )
    public void canPlace(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.placeBlocksOutsideWorldBorderFix) {
            cir.setReturnValue(
                    this.getWorld().getWorldBorder().contains(this.getBlockPos()) && (
                                    this.canReplaceExisting ||
                                    this.getWorld().getBlockState(this.getBlockPos()).canReplace(self)
                    ));
        }
    }
}

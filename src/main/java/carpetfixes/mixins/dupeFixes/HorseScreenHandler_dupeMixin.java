package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.HorseScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseScreenHandler.class)
public class HorseScreenHandler_dupeMixin {

    @Shadow
    @Final
    private HorseBaseEntity entity;


    @Inject(
            method = "transferSlot(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onlyTransferIfEntityAlive(PlayerEntity p, int i, CallbackInfoReturnable<ItemStack> cir) {
        if (CFSettings.horseDupeFix && (p.isDead() || p.isRemoved() || entity.isDead() || entity.isRemoved()))
            cir.setReturnValue(ItemStack.EMPTY);
    }
}

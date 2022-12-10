package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Add missing observer updates when a chest is open
 */

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntity_updateMixin extends BlockEntity {

    private int lastCount = 0;

    public ChestBlockEntity_updateMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Inject(
            method = "onSyncedBlockEvent(II)Z",
            at= @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/ChestLidAnimator;setOpen(Z)V"
            )
    )
    public void addObserverUpdateOnOpenAndClose(int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.missingObserverUpdatesFix && lastCount != data) {
            lastCount = data;
            Utils.giveObserverUpdates(this.world, this.pos);
        }
    }
}

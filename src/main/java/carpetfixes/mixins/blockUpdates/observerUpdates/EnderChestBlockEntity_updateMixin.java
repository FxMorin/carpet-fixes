package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderChestBlockEntity.class)
public class EnderChestBlockEntity_updateMixin extends BlockEntity {

    private int lastCount = 0;

    public EnderChestBlockEntity_updateMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
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

package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Add missing observer updates when a bell gets rung
 */

@Mixin(BellBlockEntity.class)
public class BellBlockEntity_updateMixin extends BlockEntity {

    public BellBlockEntity_updateMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Inject(
            method = "onSyncedBlockEvent(II)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/BellBlockEntity;notifyMemoriesOfBell()V"
            )
    )
    private void onSyncedBlockEventGiveObserverUpdate(int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.missingObserverUpdatesFix) Utils.giveObserverUpdates(this.world,this.pos);
    }
}

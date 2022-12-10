package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Add missing observer updates when an entity goes through an end gateway
 */

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntity_updateMixin extends BlockEntity {

    public EndGatewayBlockEntity_updateMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Inject(
            method = "onSyncedBlockEvent(II)Z",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/entity/EndGatewayBlockEntity;teleportCooldown:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void onSyncedBlockEvent(int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.missingObserverUpdatesFix) Utils.giveObserverUpdates(this.world,this.pos);
    }
}

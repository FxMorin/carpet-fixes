package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Add missing observer updates when a beacon changes power level
 */

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntity_updateMixin extends BlockEntity {

    public BeaconBlockEntity_updateMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) { // Custom SyncedBlockEvent
            Utils.giveObserverUpdates(this.world,this.pos);
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }


    @Inject(
            method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BeaconBlockEntity;)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/World;getBottomY()I"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/entity/BeaconBlockEntity;playSound(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;)V"
            )
    )
    private static void cf$onBeaconPowerChange(World world, BlockPos pos, BlockState state,
                                               BeaconBlockEntity blockEntity, CallbackInfo ci) {
        if (CFSettings.missingObserverUpdatesFix) {
            world.addSyncedBlockEvent(pos, state.getBlock(), 1, 0);
        }
    }
}

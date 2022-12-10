package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.block.entity.BeaconBlockEntity.playSound;

/**
 * Fixes beacons always playing a sound when being broken
 */
@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntity_soundMixin extends BlockEntity {

    @Shadow
    private List<BeaconBlockEntity.BeamSegment> beamSegments;

    public BeaconBlockEntity_soundMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Inject(
            method = "markRemoved()V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void markRemoved(CallbackInfo ci) {
        if (CFSettings.beaconsAlwaysPlaySoundOnBreakFix) {
            if (!this.beamSegments.isEmpty()) playSound(this.world, this.pos, SoundEvents.BLOCK_BEACON_DEACTIVATE);
            super.markRemoved();
            ci.cancel();
        }
    }
}

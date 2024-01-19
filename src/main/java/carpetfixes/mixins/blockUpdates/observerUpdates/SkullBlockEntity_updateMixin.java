package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import carpetfixes.mixins.accessors.SkullBlockEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Add missing observer updates when the skull block changes powered state
 */

@Mixin(SkullBlockEntity.class)
public class SkullBlockEntity_updateMixin {


    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cf$onTick(World world, BlockPos pos, BlockState state,
                                  SkullBlockEntity blockEntity, CallbackInfo ci) {
        if (CFSettings.missingObserverUpdatesFix) {
            SkullBlockEntityAccessor accessor = (SkullBlockEntityAccessor)blockEntity;
            if (world.isReceivingRedstonePower(pos)) {
                if (!accessor.getPowered()) {
                    Utils.giveObserverUpdates(world,pos);
                }
                accessor.setPowered(true);
                accessor.setTicksPowered(accessor.getTicksPowered()+1);
            } else {
                if (accessor.getPowered()) {
                    Utils.giveObserverUpdates(world,pos);
                }
                accessor.setPowered(false);
            }
            ci.cancel();
        }
    }
}

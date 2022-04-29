package carpetfixes.mixins.itemFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.DelayedWorldEventManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MusicDiscItem.class)
public class MusicDiscItem_worldEventMixin {


    @Redirect(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;syncWorldEvent(" +
                            "Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/util/math/BlockPos;I)V"
            )
    )
    private void worldEvent(World world, PlayerEntity playerEntity, int eventId, BlockPos blockPos, int data) {
        if (CFSettings.recordWorldEventFix) {
            DelayedWorldEventManager.addDelayedWorldEvent(world, eventId, blockPos, data);
        } else {
            world.syncWorldEvent(playerEntity, eventId, blockPos, data);
        }
    }
}

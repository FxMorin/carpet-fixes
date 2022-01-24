package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public class BoatEntity_missingOcclusionMixin {


    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    private void checkOcclusion(World instance, Entity entity, GameEvent gameEvent, BlockPos blockPos) {
        if (CarpetFixesSettings.boatMissingOcclusionFix) {
            if (instance.getBlockState(blockPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) return;
        }
        instance.emitGameEvent(entity, gameEvent, blockPos);
    }
}

package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.GT_22w11a))
@Mixin(BoatEntity.class)
public class BoatEntity_missingOcclusionMixin {


    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/Vec3d;)V"
            )
    )
    private void checkOcclusion(World instance, Entity entity, GameEvent gameEvent, Vec3d vec3d) {
        if (CFSettings.boatMissingOcclusionFix) {
            if (instance.getBlockState(entity.getBlockPos().down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) return;
        }
        instance.emitGameEvent(entity, gameEvent, vec3d);
    }
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w12a))
@Mixin(BoatEntity.class)
class BoatEntity_oldMissingOcclusionMixin {


    @SuppressWarnings("all")
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    private void checkOcclusion(World instance, Entity entity, GameEvent gameEvent, BlockPos blockPos) {
        if (CFSettings.boatMissingOcclusionFix) {
            if (instance.getBlockState(blockPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) return;
        }
        instance.emitGameEvent(entity, gameEvent, blockPos);
    }
}

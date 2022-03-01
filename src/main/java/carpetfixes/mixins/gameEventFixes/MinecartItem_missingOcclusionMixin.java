package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.item.MinecartItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.GT_22w05a))
@Mixin(MinecartItem.class)
public class MinecartItem_missingOcclusionMixin {


    @Redirect(
            method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    private void addOcclusionCheck(World world, Entity entity, GameEvent gameEvent, BlockPos blockPos) {
        if (CFSettings.minecartMissingOcclusionFix) {
            if (world.getBlockState(blockPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) return;
        }
        world.emitGameEvent(gameEvent,blockPos);
    }
}

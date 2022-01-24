package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SpawnEggItem.class)
public class SpawnEggItem_offsetAndOcclusionMixin {


    @Inject(
            method = "useOnBlock",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    public void newEventCall(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, ItemStack itemStack, BlockPos pos) {
        if (CarpetFixesSettings.spawnEggOffsetEventFix) {
            BlockState state = world.getBlockState(pos);
            BlockPos spawnPos = state.getCollisionShape(world, pos).isEmpty() ? pos : pos.offset(context.getSide());
            if (CarpetFixesSettings.spawnEggMissingOcclusionFix) {
                if (world.getBlockState(spawnPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                    return;
                }
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, spawnPos);
        } else {
            if (CarpetFixesSettings.spawnEggMissingOcclusionFix) {
                BlockState state = world.getBlockState(pos);
                BlockPos spawnPos = state.getCollisionShape(world, pos).isEmpty() ? pos : pos.offset(context.getSide());
                if (world.getBlockState(spawnPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) {
                    return;
                }
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
        }
    }


    @Redirect(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    public void cancelEvent(World instance, Entity entity, GameEvent gameEvent, BlockPos blockPos) {}
}

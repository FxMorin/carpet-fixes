package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

/**
 * Fixes two separate issues with the spawn eggs. Firstly it fixes the game event being sent from the wrong location
 * when using the egg against a block. Secondly it fixes a missing occlusion when using it on an occlusion block
 */

@Mixin(SpawnEggItem.class)
public class SpawnEggItem_offsetAndOcclusionMixin {


    @Inject(
            method = "useOnBlock",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    private void newEventCall(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir,
                              World world, ItemStack itemStack, BlockPos pos) {
        if (CFSettings.spawnEggOffsetEventFix) {
            BlockState state = world.getBlockState(pos);
            BlockPos spawnPos = state.getCollisionShape(world, pos).isEmpty() ? pos : pos.offset(context.getSide());
            if (CFSettings.spawnEggMissingOcclusionFix) {
                if (world.getBlockState(spawnPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) return;
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, spawnPos);
        } else {
            if (CFSettings.spawnEggMissingOcclusionFix) {
                BlockState state = world.getBlockState(pos);
                BlockPos spawnPos = state.getCollisionShape(world, pos).isEmpty() ?
                        pos :
                        pos.offset(context.getSide());
                if (world.getBlockState(spawnPos.down()).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS)) return;
            }
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
        }
    }


    @Inject(
            method = "spawnBaby(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/mob/MobEntity;" +
                    "Lnet/minecraft/entity/EntityType;Lnet/minecraft/server/world/ServerWorld;" +
                    "Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/item/ItemStack;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;of(Ljava/lang/Object;)Ljava/util/Optional;",
                    shift = At.Shift.BEFORE
            )
    )
    private void spawnBaby(PlayerEntity user, MobEntity entity, EntityType<? extends MobEntity> entityType,
                           ServerWorld world, Vec3d pos, ItemStack stack,
                           CallbackInfoReturnable<Optional<MobEntity>> cir) { //Using blockpos for backwards compat
        if (CFSettings.spawnEggMissingEventFix) {
            world.emitGameEvent(entity,GameEvent.ENTITY_PLACE, BlockPos.ofFloored(pos));
        }
    }


    @Redirect(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;)V"
            )
    )
    public void cancelEvent(World instance, Entity entity, GameEvent gameEvent, BlockPos blockPos) {}
}

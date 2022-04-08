package carpetfixes.mixins.gameEventFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.GT_22w13a))
@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntity_missingOcclusionMixin extends Entity {

    public ProjectileEntity_missingOcclusionMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract @Nullable Entity getOwner();


    @Inject(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/Vec3d;)V"
            )
    )
    protected void onEmittingGaveEvent(HitResult hitResult, CallbackInfo ci) {
        if (CFSettings.projectileMissingOcclusionFix && hitResult.getType() == HitResult.Type.BLOCK) {
            if (this.world.getBlockState(new BlockPos(hitResult.getPos())).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS))
                return;
        }
        this.emitGameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
    }


    @Redirect(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/Vec3d;)V"
            )
    )
    protected void cancelEmitGameEvent(World instance, Entity entity, GameEvent gameEvent, Vec3d vec3d) {}
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w14a))
@Mixin(ProjectileEntity.class)
abstract class ProjectileEntity_oldMissingOcclusionMixin extends Entity {

    public ProjectileEntity_oldMissingOcclusionMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract @Nullable Entity getOwner();


    @SuppressWarnings("all")
    @Inject(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileEntity;" +
                            "emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/entity/Entity;)V"
            )
    )
    protected void onEmittingGaveEvent(HitResult hitResult, CallbackInfo ci) {
        if (CFSettings.projectileMissingOcclusionFix && hitResult.getType() == HitResult.Type.BLOCK) {
            if (this.world.getBlockState(new BlockPos(hitResult.getPos())).isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS))
                return;
        }
        this.emitGameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
    }


    @SuppressWarnings("all")
    @Redirect(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileEntity;" +
                            "emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/entity/Entity;)V"
            )
    )
    protected void cancelEmitGameEvent(ProjectileEntity instance, GameEvent gameEvent, Entity entity) {}
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;

/**
 * When a falling block hits the ground, if the falling block damages entities it does the following:
 * 1) Checks what entities its currently touching & applied damage to those entities
 * 2) Places itself as a block
 * Boats have a hard hitbox, so the falling block does not fall through it and instead sits on it. Causing the boat
 * not to be affected by the check, and then the falling block places itself inside the boat.
 * The fix is to get all the entities that the falling block is in and also the ones where its about to be, so we
 * extend the damage hitbox area to do this. We use the entities blockpos since this is where is places the falling
 * block.
 */

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_anvilDamageMixin extends Entity {

    public FallingBlockEntity_anvilDamageMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    private Box calculateBoundsForPos(BlockPos pos) {
        EntityDimensions entityDimensions = this.getDimensions(this.getPose());
        float half = entityDimensions.width / 2.0F;
        Vec3d min = new Vec3d(
                pos.getX() - (double)half,
                pos.getY(),
                pos.getZ() - (double)half
        );
        Vec3d max = new Vec3d(
                pos.getX() + (double)half,
                pos.getY() + (double)entityDimensions.height,
                pos.getZ() + (double)half
        );
        return new Box(min, max);
    }


    @Redirect(
            method = "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"
            )
    )
    private List<Entity> handleFallDamage(World world, Entity except, Box box, Predicate<? super Entity> predicate) {
        if (CFSettings.fallingBlockDamageIsOffsetFix)
            return world.getOtherEntities(except, calculateBoundsForPos(this.getBlockPos()), predicate);
        return world.getOtherEntities(except, box, predicate);
    }
}

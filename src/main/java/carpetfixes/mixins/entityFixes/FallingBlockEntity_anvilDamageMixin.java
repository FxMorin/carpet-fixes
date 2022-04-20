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

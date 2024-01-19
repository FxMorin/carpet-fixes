package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.DirectionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fix makes it so that the bee only enters hive if either its raining where it currently is or its raining next
 * to the hive. Instead of sitting in the rain and contemplating life
 */

@Mixin(BeeEntity.class)
public abstract class BeeEntity_rainMixin extends Entity {

    public BeeEntity_rainMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    @Nullable
    BlockPos hivePos;


    @Redirect(
            method = "canEnterHive",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;isRaining()Z"
            )
    )
    private boolean cf$canEnterHive(World world) {
        if (CFSettings.beesFearRainFix && world.isRaining()) {
            if (world.hasRain(this.getBlockPos())) {
                return true;
            }
            if (this.hivePos != null) {
                BlockPos pos = this.hivePos;
                for (Direction direction : DirectionUtils.horizontal) {
                    if (world.hasRain(pos.offset(direction))) {
                        return true;
                    }
                }
            }
            return false;
        }
        return world.isRaining();
    }
}

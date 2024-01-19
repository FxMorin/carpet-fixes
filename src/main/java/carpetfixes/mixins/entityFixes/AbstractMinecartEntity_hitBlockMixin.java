package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes minecart not bouncing from any transparent block with a full face
 */

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntity_hitBlockMixin extends Entity {

    public AbstractMinecartEntity_hitBlockMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract boolean willHitBlockAt(BlockPos pos);


    @Unique
    private boolean cf$willHitBlockWithDirection(BlockPos pos, Direction dir) {
        return this.getWorld().isDirectionSolid(pos, this, dir.getOpposite()) || this.willHitBlockAt(pos);
    }


    @Redirect(
            method = "moveOnRail",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;" +
                            "willHitBlockAt(Lnet/minecraft/util/math/BlockPos;)Z",
                    ordinal = 0
            )
    )
    private boolean getHitBlockWest(AbstractMinecartEntity instance, BlockPos pos) {
        return CFSettings.minecartWontBounceFix ?
                cf$willHitBlockWithDirection(pos, Direction.WEST) : this.willHitBlockAt(pos);
    }


    @Redirect(
            method = "moveOnRail",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;" +
                            "willHitBlockAt(Lnet/minecraft/util/math/BlockPos;)Z",
                    ordinal = 1
            )
    )
    private boolean getHitBlockEast(AbstractMinecartEntity instance, BlockPos pos) {
        return CFSettings.minecartWontBounceFix ?
                cf$willHitBlockWithDirection(pos, Direction.EAST) : this.willHitBlockAt(pos);
    }


    @Redirect(
            method = "moveOnRail",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;" +
                            "willHitBlockAt(Lnet/minecraft/util/math/BlockPos;)Z",
                    ordinal = 2
            )
    )
    private boolean getHitBlockNorth(AbstractMinecartEntity instance, BlockPos pos) {
        return CFSettings.minecartWontBounceFix ?
                cf$willHitBlockWithDirection(pos, Direction.NORTH) : this.willHitBlockAt(pos);
    }


    @Redirect(
            method = "moveOnRail",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;" +
                            "willHitBlockAt(Lnet/minecraft/util/math/BlockPos;)Z",
                    ordinal = 3
            )
    )
    private boolean getHitBlockSouth(AbstractMinecartEntity instance, BlockPos pos) {
        return CFSettings.minecartWontBounceFix ?
                cf$willHitBlockWithDirection(pos, Direction.SOUTH) : this.willHitBlockAt(pos);
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fixes end crystals not triggering the ender dragon to spawn if the end crystal was moved into position using pistons
 */

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntity_pushRespawnMixin extends Entity {

    @Shadow
    public int endCrystalAge;

    @Unique
    private boolean cf$shouldTryReSpawningDragon = false;

    public EndCrystalEntity_pushRespawnMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Override
    protected Vec3d adjustMovementForPiston(Vec3d movement) {
        if (CFSettings.endCrystalsOnPushDontSummonDragonFix) {
            cf$shouldTryReSpawningDragon = true;
        }
        return super.adjustMovementForPiston(movement);
    }


    @Inject(
            method = "tick",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BEFORE
            )
    )
    private void cf$onTick(CallbackInfo ci, BlockPos blockPos) {
        if (cf$shouldTryReSpawningDragon && this.endCrystalAge % 2 == 0 &&
                this.getWorld().getBlockState(blockPos.down()).isOf(Blocks.BEDROCK)) {
            cf$shouldTryReSpawningDragon = false;
            ((ServerWorld) this.getWorld()).getEnderDragonFight().respawnDragon();
        }
    }
}

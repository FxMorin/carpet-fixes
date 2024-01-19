package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This puts the falling block back into the entity phase. Basically making it so that the block gets removed/set
 * during the entity tick. This is done since a lot of contraptions use the fact that the sand block is done in the
 * entity phase for more accurate timings
 */

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_entityPhaseMixin extends Entity {

    @Shadow
    private BlockState block;

    @Shadow
    public int timeFalling;

    public FallingBlockEntity_entityPhaseMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract BlockPos getFallingBlockPos();


    @Redirect(
            method = "spawnFromBlock(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/BlockState;)Lnet/minecraft/entity/FallingBlockEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/BlockState;I)Z"
            )
    )
    private static boolean cf$shouldSetBlock(World world, BlockPos pos, BlockState state, int flags) {
        return CFSettings.reIntroduceFallingBlockEntityPhase ||
                world.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
    }


    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            ),
            cancellable = true
    )
    private void cf$onTick(CallbackInfo ci) {
        if (CFSettings.reIntroduceFallingBlockEntityPhase && this.timeFalling == 0) {
            BlockPos pos = this.getFallingBlockPos();
            if (this.getWorld().getBlockState(pos).isOf(this.block.getBlock())) {
                this.getWorld().removeBlock(pos, false);
            } else if (!this.getWorld().isClient) {
                this.discard();
                ci.cancel();
            }
        }
    }
}

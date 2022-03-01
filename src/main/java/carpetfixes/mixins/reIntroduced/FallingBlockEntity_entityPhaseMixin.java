package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
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

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.GT_1_18_1))
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
    private static boolean shouldSetBlock(World world, BlockPos pos, BlockState state, int flags) {
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
    private void onTick(CallbackInfo ci) {
        if (CFSettings.reIntroduceFallingBlockEntityPhase && this.timeFalling == 0) {
            BlockPos pos = this.getFallingBlockPos();
            if (this.world.getBlockState(pos).isOf(this.block.getBlock())) {
                this.world.removeBlock(pos, false);
            } else if (!this.world.isClient) {
                this.discard();
                ci.cancel();
            }
        }
    }
}

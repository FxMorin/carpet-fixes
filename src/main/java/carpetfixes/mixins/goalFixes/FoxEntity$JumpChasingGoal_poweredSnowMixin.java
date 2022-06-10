package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoxEntity.JumpChasingGoal.class)
public class FoxEntity$JumpChasingGoal_poweredSnowMixin {

    
    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
        )
    )
    public boolean isOfSnowOrPoweredSnow(BlockState state, Block block) {
            return state.isOf(block) || (CFSettings.foxesDisregardPowderSnowFix && state.isOf(Blocks.POWDER_SNOW));
    }
}

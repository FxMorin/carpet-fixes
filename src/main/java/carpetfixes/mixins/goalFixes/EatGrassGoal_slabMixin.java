package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EatGrassGoal.class)
public abstract class EatGrassGoal_slabMixin extends Goal {

    @Shadow
    @Final
    private MobEntity mob;


    @Redirect(
            method = "canStart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState canStart(World instance, BlockPos blockPos) {
        if (CFSettings.sheepEatGrassThroughBlocksFix) {
            BlockPos pos = this.mob.getBlockPos();
            if (instance.getBlockState(pos).isSolidSurface(instance, pos, this.mob, Direction.DOWN))
                return Blocks.AIR.getDefaultState();
        }
        return instance.getBlockState(blockPos);
    }
}

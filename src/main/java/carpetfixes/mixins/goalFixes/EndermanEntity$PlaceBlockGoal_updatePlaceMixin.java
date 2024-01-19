package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Add the onPlaced() condition after the placeBlock which will make sure that blocks placed by the enderman
 * will be able to trigger special events such as summoning a wither. How else am I suppose to summon the wither...
 */

@Mixin(targets = "net/minecraft/entity/mob/EndermanEntity$PlaceBlockGoal")
public class EndermanEntity$PlaceBlockGoal_updatePlaceMixin {

    @Shadow
    @Final
    private EndermanEntity enderman;


    @Redirect(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/BlockState;I)Z"
            )
    )
    private boolean cf$placeCorrectly(World world, BlockPos pos, BlockState state, int flags){
        if (CFSettings.endermanDontUpdateOnPlaceFix) {
            if (world.setBlockState(pos, state, 3)) {
                state.getBlock().onPlaced(world, pos, state, enderman, state.getBlock().asItem().getDefaultStack());
                return true;
            }
            return false;
        }
        return world.setBlockState(pos, state, 3);
    }
}

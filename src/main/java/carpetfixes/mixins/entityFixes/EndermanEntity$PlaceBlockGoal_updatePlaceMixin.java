package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets="net/minecraft/entity/mob/EndermanEntity$PlaceBlockGoal")
public class EndermanEntity$PlaceBlockGoal_updatePlaceMixin {

    @Shadow @Final private EndermanEntity enderman;

    /**
     * Add the onPlaced() condition after the placeBlock which will make sure
     * that blocks placed by the enderman will be able to trigger special events
     * such as summoning a wither
     */
    @Redirect(method= "tick()V",at=@At(value="INVOKE",target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    public boolean placeCorrectly(World world, BlockPos pos, BlockState state, int flags){
        if (CarpetFixesSettings.endermanDontUpdateOnPlaceFix) {
            if (world.setBlockState(pos, state, 3)) {
                state.getBlock().onPlaced(world, pos, state, this.enderman, state.getBlock().asItem().getDefaultStack());
                return true;
            }
            return false;
        }
        return world.setBlockState(pos, state, 3);
    }
}

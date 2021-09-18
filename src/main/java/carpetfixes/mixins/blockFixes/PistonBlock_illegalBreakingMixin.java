package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value=PistonBlock.class, priority=1010)
public class PistonBlock_illegalBreakingMixin {

    /**
     * Prevents pistons from being able to break blocks with a hardness value of -1.0F.
     * Headless pistons should be required to be able to break these blocks using pistons.
     */


    @Redirect(
            method= "onSyncedBlockEvent(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;II)Z",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
            ))
    public boolean removeBlock(World world, BlockPos pos, boolean move) {
        if (CarpetFixesSettings.illegalBreakingFix && world.getBlockState(pos).getHardness(world,pos) == -1.0F) return false;
        return world.removeBlock(pos, move);
    }
}

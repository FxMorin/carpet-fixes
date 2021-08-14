package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlock.class)
public class PistonBlock_bedrockBreakingMixin {

    /**
     * Prevents pistons from being able to break bedrock. Headless pistons should be required
     * to be able to break bedrock using pistons.
     */


    @Redirect(
            method= "onSyncedBlockEvent(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;II)Z",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
            ))
    public boolean removeBlock(World world, BlockPos pos, boolean move) {
        if (CarpetFixesSettings.bedrockBreakingFix && world.getBlockState(pos).isOf(Blocks.BEDROCK)) return false;
        return world.removeBlock(pos, move);
    }
}

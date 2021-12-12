package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockPos.class)
public abstract class BlockPos_incorrectDistanceMixin extends Vec3i {

    public BlockPos_incorrectDistanceMixin(int x, int y, int z) {super(x, y, z);}

    /**
     * Thanks to 2No2Name for noticing that `BlockPos.ORIGIN.getSquaredDistance(BlockPos.ORIGIN)`
     * returns 0.75 xD. This is due to `getSquaredDistance` automatically setting `treatAsBlockPos`
     * to true, which in this case shouldn't happen since it's already a BlockPos.
     *
     * The fix is simply to override getSquaredDistance in BlockPos so `treatAsBlockPos` is set to false.
     *
     * This method was not intended to be used against 2 blockPos, although Mojang does it... So we need to fix it.
     */


    @Override
    public double getSquaredDistance(Vec3i vec) {
        return this.getSquaredDistance(vec.getX(), vec.getY(), vec.getZ(), !CarpetFixesSettings.incorrectBlockPosDistanceFix);
    }
}

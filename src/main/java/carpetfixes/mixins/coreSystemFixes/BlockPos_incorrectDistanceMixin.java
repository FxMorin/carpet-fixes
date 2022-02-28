package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.LT_22w05a))
@Mixin(BlockPos.class)
public abstract class BlockPos_incorrectDistanceMixin extends Vec3i {

    public BlockPos_incorrectDistanceMixin(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public double getSquaredDistance(Vec3i vec) {
        double add = CFSettings.incorrectBlockPosDistanceFix ? 0.5 : 0;
        double d = (double)this.getX() + add - (double)vec.getX();
        double e = (double)this.getY() + add - (double)vec.getY();
        double f = (double)this.getZ() + add - (double)vec.getZ();
        return d * d + e * e + f * f;
    }
}

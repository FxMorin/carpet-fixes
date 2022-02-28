package carpetfixes.mixins.parity;

import carpetfixes.CFSettings;
import net.minecraft.block.ComparatorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ComparatorBlock.class)
public class ComparatorBlock_terribleMixin {

    @ModifyConstant(
            method = "getPower(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/BlockState;)I",
            constant = @Constant(
                    intValue = 15,
                    ordinal = 0
            )
    )
    protected int modifyPower(int constant) {
        return CFSettings.parityTerribleComparators ? 16 : constant;
    }
}

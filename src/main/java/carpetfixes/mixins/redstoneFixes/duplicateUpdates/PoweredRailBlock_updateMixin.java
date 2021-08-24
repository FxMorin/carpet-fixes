package carpetfixes.mixins.redstoneFixes.duplicateUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.PoweredRailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PoweredRailBlock.class)
public abstract class PoweredRailBlock_updateMixin {


    @ModifyArg(
            method = "updateBlockState",
            at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            index = 2
    )
    private int modifyUpdate(int val) {
        return CarpetFixesSettings.duplicateBlockUpdatesFix ? 2 : 3;
    }
}

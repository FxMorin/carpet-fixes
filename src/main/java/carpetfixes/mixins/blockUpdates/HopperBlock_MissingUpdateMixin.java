package carpetfixes.mixins.blockUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.block.HopperBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HopperBlock.class)
public class HopperBlock_MissingUpdateMixin extends Block {

    public HopperBlock_MissingUpdateMixin(Settings settings) {super(settings);}

    /**
     * The hopper when placed next to a power source does not give the
     * correct block updates, causing some unintended behaviour. We make
     * sure to give that correct update here by adding binary 1 to the update.
     * Which will add block updates, fixing all the issues.
     */
    @ModifyArg(method = "updateEnabled(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), index = 2)
    protected int hopperUpdate(int value) { return CarpetFixesSettings.hopperUpdateFix ? 5 : 4; }
}

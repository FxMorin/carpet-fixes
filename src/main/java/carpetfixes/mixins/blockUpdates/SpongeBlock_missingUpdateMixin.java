package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.SpongeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * The sponge when placed next to a water source does not give a block update, causing some unintended behaviour
 * where you can update suppress on place. We make sure to give that correct update here by adding binary
 * 1 (NOTIFY_NEIGHBORS) to the update value. This will add a block updates, fixing all the issues.
 */

@Mixin(SpongeBlock.class)
public class SpongeBlock_missingUpdateMixin extends Block {

    public SpongeBlock_missingUpdateMixin(Settings settings) {
        super(settings);
    }


    @ModifyConstant(
            method = "update(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            require = 0,
            constant = @Constant(intValue = 2)
    )
    private int cf$spongeUpdate(int val) {
        return CFSettings.spongeUpdateFix ? val | Block.NOTIFY_NEIGHBORS : val;
    }
}

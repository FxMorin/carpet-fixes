package carpetfixes.mixins.blockUpdates;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import carpetfixes.CarpetFixesSettings;

@Mixin(SpongeBlock.class)
public class SpongeBlock_missingUpdateMixin extends Block {

    /**
     * The sponge when placed next to a water source does not give a block update, causing
     * some unintended behaviour where you can update suppress on place. We make sure to give
     * that correct update here by adding binary 1 to the update value. This will add a block
     * update, fixing all the issues.
     */


    public SpongeBlock_missingUpdateMixin(Settings settings) {super(settings);}


    @ModifyConstant(
            method = "update(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            require = 0,
            constant = @Constant(intValue = 2)
    )
    protected int spongeUpdate(int value) {
        return CarpetFixesSettings.spongeUpdateFix && value%2 == 0 ? ++value : value;
    }
}

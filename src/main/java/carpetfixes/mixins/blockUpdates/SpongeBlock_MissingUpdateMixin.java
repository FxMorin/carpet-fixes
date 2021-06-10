package carpetfixes.mixins.blockUpdates;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import carpetfixes.CarpetFixesSettings;

@Mixin(SpongeBlock.class)
public class SpongeBlock_MissingUpdateMixin extends Block {

    public SpongeBlock_MissingUpdateMixin(Settings settings) {super(settings);}

    /**
     * The sponge when placed next to water does not give the correct block
     * updates, causing some unintended behaviour. We make sure to give that
     * correct update here by adding binary 1 to the update. Which will add
     * block updates, fixing all the issues.
     */
    @ModifyArg(method = "update(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), index = 2)
    protected int spongeUpdate(int value) {
        return CarpetFixesSettings.spongeUpdateFix ? 3 : 2;
    }
}

package carpetfixes.mixins.blockUpdates;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import carpetfixes.CarpetFixesSettings;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpongeBlock.class)
public class SpongeBlock_MissingUpdateMixin extends Block {

    public SpongeBlock_MissingUpdateMixin(Settings settings) {super(settings);}

    @Inject(method = "update(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),cancellable = true)
    protected void spongeUpdate(World world, BlockPos pos, CallbackInfo ci) {
        if (CarpetFixesSettings.spongeUpdateFix) {
            world.setBlockState(pos, Blocks.WET_SPONGE.getDefaultState(),3);
            ci.cancel();
        }
    }
}

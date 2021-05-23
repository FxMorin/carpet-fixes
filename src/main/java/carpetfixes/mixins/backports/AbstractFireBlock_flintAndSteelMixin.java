package carpetfixes.mixins.backports;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlock_flintAndSteelMixin {
    @Inject(method = "method_30032", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;canPlaceAt(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"), cancellable = true)
    private static void canPlaceAt(World world, BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir){
        if(CarpetFixesSettings.oldFlintAndSteelBehavior) cir.setReturnValue(true);
    }
}

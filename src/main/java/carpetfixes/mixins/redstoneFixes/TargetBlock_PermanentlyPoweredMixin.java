package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.TargetBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TargetBlock.class)
public class TargetBlock_PermanentlyPoweredMixin {

    @Inject(method = "onBlockAdded(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V", at = @At(value = "INVOKE"),cancellable = true)
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (CarpetFixesSettings.targetBlockPermanentlyPoweredFix) {
            ci.cancel();
        }
    }
}

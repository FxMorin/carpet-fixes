package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlock_worldBorderMixin {


    @Inject(
            method = "isMovable(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;ZLnet/minecraft/util/math/Direction;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void customWorldBorderCheck(BlockState state, World world, BlockPos pos, Direction dir, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.incorrectPistonWorldBorderCheckFix && !world.getWorldBorder().contains(pos.offset(dir))) {
            cir.setReturnValue(false);
        }
    }


    @Redirect(
            method = "isMovable(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;ZLnet/minecraft/util/math/Direction;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/border/WorldBorder;contains(Lnet/minecraft/util/math/BlockPos;)Z"
            )
    )
    private static boolean customWorldBorderCheck(WorldBorder instance, BlockPos pos) {
        return CFSettings.incorrectPistonWorldBorderCheckFix || instance.contains(pos);
    }
}

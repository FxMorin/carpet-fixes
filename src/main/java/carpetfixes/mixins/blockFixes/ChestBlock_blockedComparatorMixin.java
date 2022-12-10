package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.ChestBlock.getInventory;

/**
 * Fixes comparators checking blocked chests
 */
@Mixin(ChestBlock.class)
public class ChestBlock_blockedComparatorMixin {

    ChestBlock self = (ChestBlock)(Object)this;


    @Inject(
            method = "getComparatorOutput",
            at = @At("HEAD"),
            cancellable = true
    )
    private void modifyCompOutput(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!CFSettings.comparatorSkipsBlockedChestFix) return;
        cir.setReturnValue(ScreenHandler.calculateComparatorOutput(getInventory(self, state, world, pos, true)));
    }
}

package carpetfixes.mixins.dupeFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.TripwireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Fixes the wring dupe at the cost of removing the ability to disarm tripwire. Wait you didn't know you could do that?
 * That's why I decided to do it xD
 */

@Mixin(TripwireBlock.class)
public class TripwireBlock_stringDupeMixin {


    @ModifyArg(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/TripwireHookBlock;update(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;" +
                            "ZZILnet/minecraft/block/BlockState;)V"
            ),
            index = 5
    )
    private int cf$alwaysNegativeOne(int i) {
        return CFSettings.stringDupeFix ? -1 : i;
    }
}

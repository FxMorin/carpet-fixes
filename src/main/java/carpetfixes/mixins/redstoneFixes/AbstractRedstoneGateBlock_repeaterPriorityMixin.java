package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractRedstoneGateBlock.class)
public class AbstractRedstoneGateBlock_repeaterPriorityMixin extends HorizontalFacingBlock  {

    /**
     * Repeater Priority is an interesting problem. DeadlyMC's original implementation
     * was slightly broken, although we were able to fix it so that it works perfectly
     */


    protected AbstractRedstoneGateBlock_repeaterPriorityMixin(Settings settings) {super(settings);}


    @Redirect(
            method = "isTargetNotAligned",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;get(Lnet/minecraft/state/property/Property;)Ljava/lang/Comparable;",
                    ordinal = 1
            ))
    private <T extends Comparable<T>> T onIsTargetNotAligned(BlockState blockState, Property<T> property) {
        return (CFSettings.repeaterPriorityFix) ? (T) blockState.get(FACING).getOpposite() : (T) blockState.get(FACING);
    }
}

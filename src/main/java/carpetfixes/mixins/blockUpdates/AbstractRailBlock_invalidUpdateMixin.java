package carpetfixes.mixins.blockUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractRailBlock.class)
public abstract class AbstractRailBlock_invalidUpdateMixin extends Block {

    public AbstractRailBlock_invalidUpdateMixin(Settings settings) {
        super(settings);
    }

    @Shadow public abstract Property<RailShape> getShapeProperty();
    @Shadow private static boolean shouldDropRail(BlockPos pos, World world, RailShape shape) { return true;}

    /**
     * The issue here is that the rail updates the other rails near it before
     * checking if its in a valid place. This should be the other way round,
     * we check if its in a valid place and if its not, then we break the rail
     * on if its in a valid place do we update the rails near it.
     */
    @Inject(method = "onBlockAdded(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V", at = @At("HEAD"), cancellable = true)
    private void updateNeighborsExceptWithBetterDirection(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (CarpetFixesSettings.railInvalidUpdateOnPushFix) {
            RailShape railShape = state.get(this.getShapeProperty());
            if (shouldDropRail(pos, world, railShape)) {
                dropStacks(state, world, pos);
                world.removeBlock(pos, notify);
                ci.cancel();
            }
        }
    }
}

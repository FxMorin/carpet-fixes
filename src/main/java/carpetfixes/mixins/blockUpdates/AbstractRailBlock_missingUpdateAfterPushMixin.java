package carpetfixes.mixins.blockUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractRailBlock.class)
public abstract class AbstractRailBlock_missingUpdateAfterPushMixin extends Block {

    public AbstractRailBlock_missingUpdateAfterPushMixin(Settings settings) {
        super(settings);
    }

    @Shadow public abstract Property<RailShape> getShapeProperty();
    @Shadow @Final private boolean allowCurves;

    @Inject(method = "onBlockAdded(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V", at = @At("HEAD"), cancellable = true)
    protected void alwaysGiveUpdate(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (CarpetFixesSettings.railMissingUpdateAfterPushFix) {
            if (!oldState.isOf(state.getBlock())) {
                if ((state.get(this.getShapeProperty())).isAscending()) {
                    world.updateNeighborsAlways(pos.up(), this);
                }
                if (this.allowCurves) {
                    world.updateNeighborsAlways(pos, this);
                    world.updateNeighborsAlways(pos.down(), this);
                }
            }
            ci.cancel();
        }
    }
}

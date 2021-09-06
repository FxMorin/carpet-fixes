package carpetfixes.mixins.blockUpdates.duplicateUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PoweredRailBlock.class)
public abstract class PoweredRailBlock_updateMixin {


    PoweredRailBlock self = (PoweredRailBlock)(Object)this;


    @Shadow @Final public static EnumProperty<RailShape> SHAPE;
    @Shadow @Final public static BooleanProperty POWERED;
    @Shadow protected boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean bl, int distance) { return true; }


    @ModifyArg(
            method = "updateBlockState",
            at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            index = 2
    )
    private int modifyUpdate(int val) {
        return CarpetFixesSettings.duplicateBlockUpdatesFix ? 2 : 3;
    }


    @Inject(
            method= "updateBlockState(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            at=@At("HEAD"),
            cancellable = true
    )
    protected void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor, CallbackInfo ci) {
        if (CarpetFixesSettings.uselessSelfBlockUpdateFix) {
            boolean bl = state.get(POWERED);
            boolean bl2 = world.isReceivingRedstonePower(pos) || this.isPoweredByOtherRails(world, pos, state, true, 0) || this.isPoweredByOtherRails(world, pos, state, false, 0);
            if (bl2 != bl) {
                world.setBlockState(pos, state.with(POWERED, bl2), 3);
                world.updateNeighborsExcept(pos.down(),self, Direction.UP);
                if ((state.get(SHAPE)).isAscending()) {
                    world.updateNeighborsExcept(pos.down(),self, Direction.DOWN);
                }
            }
            ci.cancel();
        }
    }
}

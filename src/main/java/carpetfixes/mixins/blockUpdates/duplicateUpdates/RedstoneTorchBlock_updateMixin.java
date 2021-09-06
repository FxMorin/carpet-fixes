package carpetfixes.mixins.blockUpdates.duplicateUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedstoneTorchBlock.class)
public abstract class RedstoneTorchBlock_updateMixin extends TorchBlock {


    RedstoneTorchBlock self = (RedstoneTorchBlock)(Object)this;


    @Shadow @Final public static BooleanProperty LIT;


    protected RedstoneTorchBlock_updateMixin(Settings settings, ParticleEffect particle) {super(settings, particle);}


    @Inject(
            method="onStateReplaced",
            at=@At("HEAD"),
            cancellable = true
    )
    public void onStateReplacedBetter(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if (CarpetFixesSettings.duplicateBlockUpdatesFix) {
            if (!moved && !state.isOf(newState.getBlock())) { //Added missing: !state.isOf(newState.getBlock())
                if (state.get(LIT)) { //Added missing: state.get(LIT)
                    Direction[] var6 = Direction.values();
                    int var7 = var6.length;
                    for (int var8 = 0; var8 < var7; ++var8) {
                        Direction direction = var6[var8];
                        if (CarpetFixesSettings.uselessSelfBlockUpdateFix) { //Must include in this
                            world.updateNeighborsExcept(pos.offset(direction), self, direction.getOpposite());
                        } else {
                            world.updateNeighborsAlways(pos.offset(direction), self);
                        }
                    }
                }
                super.onStateReplaced(state, world, pos, newState, moved); //Added missing: super.onStateReplaced(...)
            }
            ci.cancel();
        } else if (CarpetFixesSettings.uselessSelfBlockUpdateFix) { // Do CarpetFixesSettings.uselessSelfBlockUpdateFix here
            if (!moved) {
                Direction[] var6 = Direction.values();
                int var7 = var6.length;
                for(int var8 = 0; var8 < var7; ++var8) {
                    Direction direction = var6[var8];
                    world.updateNeighborsExcept(pos.offset(direction),self,direction.getOpposite());
                }
            }
            ci.cancel();
        }
    }

    @Inject(
            method="onBlockAdded",
            at=@At("HEAD"),
            cancellable = true
    )
    public void onBlockAddedBetter(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if (CarpetFixesSettings.uselessSelfBlockUpdateFix) {
            Direction[] var6 = Direction.values();
            int var7 = var6.length;
            for (int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                world.updateNeighborsExcept(pos.offset(direction),self,direction.getOpposite());
            }
            ci.cancel();
        }
    }
}

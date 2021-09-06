package carpetfixes.mixins.blockUpdates.duplicateUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractPressurePlateBlock.class)
public class AbstractPressurePlateBlock_uselessMixin {


    AbstractPressurePlateBlock self = (AbstractPressurePlateBlock)(Object)this;


    @Inject(
            method= "updateNeighbors(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at=@At("HEAD"),
            cancellable = true
    )
    protected void updateNeighbors(World world, BlockPos pos, CallbackInfo ci) {
        if (CarpetFixesSettings.uselessSelfBlockUpdateFix) {
            world.updateNeighborsAlways(pos, self);
            world.updateNeighborsExcept(pos.down(),self, Direction.UP);
        }
    }
}

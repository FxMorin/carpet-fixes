package carpetfixes.mixins.blockUpdates;

import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.Block;
import net.minecraft.class_7164;
import net.minecraft.class_7165;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(class_7164.class) // InstantNeighborUpdater
public abstract class class_7164_updateOrderMixin implements class_7165 {


    @Override
    public void method_41705(BlockPos blockPos, Block block, @Nullable Direction direction) {
        for(Direction direction2 : BlockUpdateUtils.blockUpdateDirections.apply(blockPos)) {
            if (direction2 != direction) this.method_41704(blockPos.offset(direction2), block, blockPos);
        }
    }
}

package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.TrapdoorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlock_skipperMixin {

    @WrapOperation(
            method = "getRenderConnectionType(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/util/math/Direction;Z)Lnet/minecraft/block/enums/WireConnection;",
            constant = @Constant(classValue = TrapdoorBlock.class)
    )
    private boolean trapdoorUpdateSkipping(Object obj, Operation<Boolean> original) {
        return !CFSettings.reIntroduceTrapdoorUpdateSkipping && original.call(obj);
    }
}

package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(PistonBlock.class)
public class PistonBlock_pushOrderMixin {


    @Redirect(
            method = "move(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/util/math/Direction;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;"
            )
    )
    private HashMap<BlockPos, BlockState> hashmapBadYeeeet() {
        return CFSettings.pistonUpdateOrderIsLocationalFix ? Maps.newLinkedHashMap() : Maps.newHashMap();
    }
}

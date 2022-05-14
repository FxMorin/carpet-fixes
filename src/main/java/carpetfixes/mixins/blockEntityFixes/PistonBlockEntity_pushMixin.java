package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Boxes;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_pushMixin {


    @Redirect(
            method = "pushEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Boxes;stretch(Lnet/minecraft/util/math/Box;" +
                            "Lnet/minecraft/util/math/Direction;D)Lnet/minecraft/util/math/Box;"
            )
    )
    private static Box stretchCorrectly(Box box, Direction direction, double length) {
        return CFSettings.pistonsPushEntitiesBehindThemFix ?
                Utils.stretchBlockBound(box, direction, length)  : Boxes.stretch(box, direction, length);
    }
}
package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.AreaHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaHelper.class)
public class AreaHelper_updateMixin {

    @Shadow
    private @Nullable BlockPos lowerCorner;

    @Shadow
    private int height;

    @Shadow
    @Final
    private Direction negativeDir;

    @Shadow
    @Final
    private int width;

    @Shadow
    @Final
    private WorldAccess world;

    @Shadow
    @Final
    private Direction.Axis axis;


    @Inject(
            method = "createPortal()V",
            at = @At("RETURN")
    )
    public void createPortal(CallbackInfo ci) {
        if (CFSettings.missingObserverUpdatesFix) {
            BlockPos.iterate(
                            this.lowerCorner,
                            this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1)
                    )
                    .forEach(blockPos -> {
                        if (this.axis == Direction.Axis.X) {
                            Utils.giveObserverUpdate((World)this.world, blockPos, Direction.NORTH);
                            Utils.giveObserverUpdate((World)this.world, blockPos, Direction.SOUTH);
                        } else { // Z
                            Utils.giveObserverUpdate((World)this.world, blockPos, Direction.WEST);
                            Utils.giveObserverUpdate((World)this.world, blockPos, Direction.EAST);
                        }
                    });
        }
    }
}

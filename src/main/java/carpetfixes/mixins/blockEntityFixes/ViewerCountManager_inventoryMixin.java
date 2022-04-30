package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ViewerCountManager.class)
public class ViewerCountManager_inventoryMixin {

    @Shadow
    private int viewerCount;


    @Inject(
            method = "closeContainer",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/entity/ViewerCountManager;viewerCount:I",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    public void clampViewerCount(PlayerEntity player, World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CFSettings.viewerCountNegativesFix && this.viewerCount < 0) this.viewerCount = 0;
    }
}

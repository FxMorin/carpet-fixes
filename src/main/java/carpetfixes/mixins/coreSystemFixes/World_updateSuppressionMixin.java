package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class World_updateSuppressionMixin {
    @Inject(method = "updateNeighbor", at = @At(value = "HEAD", target = "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/util/crash/CrashReport;"), cancellable = true)
    public void updateNeighbor(BlockPos sourcePos, Block sourceBlock, BlockPos neighborPos, CallbackInfo ci) {
        if (CarpetFixesSettings.updateSuppressionFix) {
            ci.cancel();
        }
    }
}

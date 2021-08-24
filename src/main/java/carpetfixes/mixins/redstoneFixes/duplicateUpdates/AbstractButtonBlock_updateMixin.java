package carpetfixes.mixins.redstoneFixes.duplicateUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.AbstractButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractButtonBlock.class)
public class AbstractButtonBlock_updateMixin {


    @ModifyArg(
            method = "tryPowerWithProjectiles",
            at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            index = 2
    )
    private int modifyProjectileUpdate(int val) {
        return CarpetFixesSettings.duplicateBlockUpdatesFix ? 2 : 3;
    }


    @ModifyArg(
            method = "powerOn",
            at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            index = 2
    )
    private int modifyPowerUpdate(int val) {
        return CarpetFixesSettings.duplicateBlockUpdatesFix ? 2 : 3;
    }


    @ModifyArg(
            method = "scheduledTick",
            at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
            index = 2
    )
    private int modifyScheduledUpdate(int val) {
        return CarpetFixesSettings.duplicateBlockUpdatesFix ? 2 : 3;
    }
}

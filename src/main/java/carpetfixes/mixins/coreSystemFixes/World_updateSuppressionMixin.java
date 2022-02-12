package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.UpdateScheduler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class World_updateSuppressionMixin {

    /**
     * Update Suppression can cause server crashes & exploits. What we do here is hyjack
     * neighborUpdate to catch stackOverflow errors, we then throw that error to the parent
     * try-catch where the second mixin can add a scheduled update so that it can be updated
     * in the next tick.
     * This fix DOES NOT completely fix update suppression, some exploits are still possible.
     * This will prevent using an update suppressor to crash the server, while also
     * preventing blocks from being in illegal states. Although a suppressor will still
     * cause crashes in the block code, which could be used for some exploits
     * E.x. Soulbound chests & Item Shadowing
     */


    private final World self = (World)(Object)this;


    @Redirect(
            method = "updateNeighbor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;neighborUpdate(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;Z)V"
            ))
    public void betterUpdateNeighbour(BlockState blockState, World world, BlockPos pos, Block block, BlockPos posFrom, boolean notify){
        if (CarpetFixesSettings.updateSuppressionFix) {
            try {
                blockState.neighborUpdate(world, pos, block, posFrom, notify);
            } catch (Throwable var7) {
                throw new StackOverflowError();
            }
            return;
        }
        blockState.neighborUpdate(world, pos, block, posFrom, notify);
    }


    @Inject(
            method = "updateNeighbor",
            at = @At(
                    shift= At.Shift.BEFORE,
                    value="INVOKE",
                    target = "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/util/crash/CrashReport;"
            ),
            cancellable = true
    )
    public void checkUpdateSuppression(BlockPos sourcePos, Block sourceBlock, BlockPos neighborPos, CallbackInfo ci) {
        if(CarpetFixesSettings.updateSuppressionFix) {
            CarpetFixesSettings.updateScheduler.get(self).addScheduledUpdate(new UpdateScheduler.ScheduledUpdate(sourcePos, sourceBlock));
            ci.cancel();
        }
    }
}


package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import com.google.common.collect.Sets;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.GT_22w11a))
@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlock_missingUpdateMixin extends Block {

    ThreadLocal<Boolean> needsUpdate = ThreadLocal.withInitial(() -> false);

    public RedstoneWireBlock_missingUpdateMixin(Settings settings) {
        super(settings);
    }


    @Inject(
            method = "prepare(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldAccess;method_42308(Lnet/minecraft/util/math/Direction;" +
                            "Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/util/math/BlockPos;II)V"
            )
    )
    public void shouldUpdate(BlockState state, WorldAccess world, BlockPos pos,
                             int flags, int maxUpdateDepth, CallbackInfo ci) {
        needsUpdate.set(true);
    }


    @Inject(
            method = "prepare(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;" +
                    "Lnet/minecraft/util/math/BlockPos;II)V",
            at = @At("TAIL")
    )
    public void doUpdate(BlockState state, WorldAccess world, BlockPos pos, int flags, int d, CallbackInfo ci) {
        if (CFSettings.redstoneRedirectionMissingUpdateFix && needsUpdate.get()) {
            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();
            for (Direction direction : var6) set.add(pos.offset(direction));
            for (BlockPos blockPos : set) ((World) world).updateNeighborsAlways(blockPos, this);
        }
        needsUpdate.set(false);
    }
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w12a))
@Mixin(RedstoneWireBlock.class)
abstract class RedstoneWireBlock_oldMissingUpdateMixin extends Block {

    ThreadLocal<Boolean> needsUpdate = ThreadLocal.withInitial(() -> false);

    public RedstoneWireBlock_oldMissingUpdateMixin(Settings settings) {
        super(settings);
    }


    @SuppressWarnings("all")
    @Inject(
            method = "prepare(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getStateForNeighborUpdate(" +
                            "Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;" +
                            "Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"
            )
    )
    public void shouldUpdate(BlockState state, WorldAccess world, BlockPos pos,
                             int flags, int maxUpdateDepth, CallbackInfo ci) {
        needsUpdate.set(true);
    }


    @Inject(
            method = "prepare(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;" +
                    "Lnet/minecraft/util/math/BlockPos;II)V",
            at = @At("TAIL")
    )
    public void doUpdate(BlockState state, WorldAccess world, BlockPos pos, int flags, int d, CallbackInfo ci) {
        if (CFSettings.redstoneRedirectionMissingUpdateFix && needsUpdate.get()) {
            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();
            for (Direction direction : var6) set.add(pos.offset(direction));
            for (BlockPos blockPos : set) ((World) world).updateNeighborsAlways(blockPos, this);
        }
        needsUpdate.set(false);
    }
}

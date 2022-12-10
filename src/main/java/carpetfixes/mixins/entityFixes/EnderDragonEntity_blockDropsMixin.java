package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fixes the enderdragon block breaking not dropping the blocks it breaks
 */

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntity_blockDropsMixin extends MobEntity {

    protected EnderDragonEntity_blockDropsMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "destroyBlocks",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z",
                    shift = At.Shift.BEFORE
            ),
            require = 0
    )
    private void letsDropTheBlocks(Box box, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int m,
                                   int n, boolean bl, boolean bl2, int o, int p, int q, BlockPos pos) {
        if (CFSettings.enderDragonDoesntDropBlocksFix) {
            BlockState blockState = this.world.getBlockState(pos);
            BlockEntity blockEntity = blockState.hasBlockEntity() ? this.world.getBlockEntity(pos) : null;
            Block.dropStacks(blockState, this.world, pos, blockEntity, null, ItemStack.EMPTY);
        }
    }
}

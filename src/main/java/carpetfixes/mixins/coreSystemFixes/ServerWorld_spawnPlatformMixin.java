package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.server.world.ServerWorld.END_SPAWN_POS;

/**
 * When the obsidian platform is generated in the end, it breaks all the blocks above. Instead we remove all end stone
 * and if the 2 middle blocks are not empty we break a larger area with block drop.
 */

@Mixin(ServerWorld.class)
public class ServerWorld_spawnPlatformMixin {


    @Inject(
            method = "createEndSpawnPlatform",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void createCustomEndSpawnPlatform(ServerWorld world, CallbackInfo ci) {
        if (CFSettings.obsidianPlatformDestroysBlocksFix) {
            BlockPos blockPos = END_SPAWN_POS;
            int x = blockPos.getX();
            int y = blockPos.getY() - 2;
            int z = blockPos.getZ();
            BlockPos.iterate(x - 2, y + 1, z - 2, x + 2, y + 3, z + 2).forEach(pos -> {
                if (world.getBlockState(pos).isOf(Blocks.END_STONE))
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
            });
            BlockPos.iterate(x - 2, y, z - 2, x + 2, y, z + 2).forEach(pos ->
                    world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState())
            );
            if (!world.isAir(blockPos) || !world.isAir(blockPos.up())) { //obv would do an entityType check instead
                BlockPos.iterate(x - 1, y + 1, z - 1, x + 1, y + 2, z + 1).forEach(pos -> {
                    world.breakBlock(pos, true);
                });
            }
            ci.cancel();
        }
    }
}

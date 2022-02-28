package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_zeroTickMixin extends World {

    protected ServerWorld_zeroTickMixin(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session,
                                        ServerWorldProperties properties, RegistryKey<World> worldKey,
                                        RegistryEntry<DimensionType> registryEntry,
                                        WorldGenerationProgressListener worldGenerationProgressListener,
                                        ChunkGenerator chunkGenerator, boolean debugWorld, long seed,
                                        List<Spawner> spawners, boolean shouldTickTime) {
        super(properties, worldKey, registryEntry, server::getProfiler, false, debugWorld, seed);
    }

    private final ServerWorld self = (ServerWorld)(Object)this;


    @Inject(
            method = "tickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;scheduledTick(Lnet/minecraft/server/world/ServerWorld;" +
                            "Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void zeroTickBlock(BlockPos pos, Block block, CallbackInfo ci, BlockState state) {
        if (CFSettings.reIntroduceZeroTickFarms && !this.isAir(pos) && state.hasRandomTicks())
            state.randomTick(self,pos,this.random);
    }
}

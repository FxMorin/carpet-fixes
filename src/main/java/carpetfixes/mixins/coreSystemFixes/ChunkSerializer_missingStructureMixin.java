package carpetfixes.mixins.coreSystemFixes;


import carpetfixes.CarpetFixesSettings;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.ChunkTickScheduler;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.EnumSet;
import java.util.Map;

@Mixin(ChunkSerializer.class)
public class ChunkSerializer_missingStructureMixin {

    private static final ThreadLocal<Boolean> preSaveList = ThreadLocal.withInitial(() -> false);


    @Inject(
            method = "readStructureReferences",
            require = 0,
            at = @At(
                    value="INVOKE",
                    target="Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 0
            ))
    private static void readInvalidStructures(ChunkPos pos, NbtCompound nbt, CallbackInfoReturnable<Map<StructureFeature<?>, LongSet>> cir) {
        if (CarpetFixesSettings.missingStructureCorruptionFix) {
            ChunkSerializer_missingStructureMixin.preSaveList.set(true);
        }
    }


    @Inject(
            method = "deserialize",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/chunk/Chunk;setStructureReferences(Ljava/util/Map;)V",
                    shift = At.Shift.AFTER
            ))
    private static void saveWithoutInvalidStructures(ServerWorld world, StructureManager arg1, PointOfInterestStorage poiStorage, ChunkPos pos, NbtCompound nbt, CallbackInfoReturnable<ProtoChunk> cir, ChunkGenerator chunkGenerator, BiomeSource biomeSource, NbtCompound nbtCompound, BiomeArray biomeArray, UpgradeData upgradeData, ChunkTickScheduler chunkTickScheduler, ChunkTickScheduler chunkTickScheduler2, boolean bl, NbtList nbtList, int i, ChunkSection chunkSections[], boolean bl2, ChunkManager chunkManager, LightingProvider lightingProvider, long l, ChunkStatus.ChunkType chunkType, Chunk chunk2, NbtCompound nbtCompound3, EnumSet enumSet, NbtCompound nbtCompound4) {
        if (ChunkSerializer_missingStructureMixin.preSaveList.get()) {
            ChunkSerializer_missingStructureMixin.preSaveList.set(false);
            chunk2.setShouldSave(true);
        }
    }
}

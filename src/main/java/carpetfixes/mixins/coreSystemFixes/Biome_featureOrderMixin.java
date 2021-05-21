package carpetfixes.mixins.coreSystemFixes;

import carpet.CarpetServer;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Biome.class)
public class Biome_featureOrderMixin {

    @Inject(
            method = "generateFeatureStep(Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/world/ChunkRegion;JLnet/minecraft/world/gen/ChunkRandom;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;size()I")
    )
    public void registerOverride(StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, ChunkRegion region, long populationSeed, ChunkRandom random, BlockPos pos, CallbackInfo ci) {
        if (CarpetFixesSettings.worldgenIncorrectOrderFix) {
            try {
                new LakeFeature(SingleStateFeatureConfig.CODEC).generate(CarpetServer.minecraft_server.getOverworld(), chunkGenerator,random,pos,new SingleStateFeatureConfig(region.getBlockState(pos)));
            } catch (Exception var22) {
                CrashReport crashReport2 = CrashReport.create(var22, "Lakes Mixin - Feature placement");
                throw new CrashException(crashReport2);
            }
        }
    }
}

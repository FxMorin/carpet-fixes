package carpetfixes.mixins.featureFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(targets = "net.minecraft.world.gen.feature.BuriedTreasureFeature$Start")
public abstract class BuriedTreasureFeature$Start_centerMixin extends StructureStart<ProbabilityConfig> {

    public BuriedTreasureFeature$Start_centerMixin(StructureFeature<ProbabilityConfig> feature, ChunkPos pos, int references, long seed) {super(feature, pos, references, seed);}

    @Inject(
            method = "init(Lnet/minecraft/util/registry/DynamicRegistryManager;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/structure/StructureManager;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/biome/Biome;Lnet/minecraft/world/gen/ProbabilityConfig;Lnet/minecraft/world/HeightLimitView;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, ProbabilityConfig probabilityConfig, HeightLimitView heightLimitView, CallbackInfo ci) {
        if (CarpetFixesSettings.buriedTreasureAlwaysCenterFix) {
            Random rand = new Random(chunkPos.toLong());
            BlockPos blockPos = new BlockPos(chunkPos.getOffsetX(rand.nextInt(16)), 90, chunkPos.getOffsetZ(rand.nextInt(16)));
            this.addPiece(new BuriedTreasureGenerator.Piece(blockPos));
            ci.cancel();
        }
    }

    @Inject(
            method = "getBlockPos()Lnet/minecraft/util/math/BlockPos;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getBlockPos(CallbackInfoReturnable<BlockPos> cir) {
        if (CarpetFixesSettings.buriedTreasureAlwaysCenterFix) {
            ChunkPos chunkPos = this.getPos();
            Random rand = new Random(chunkPos.toLong());
            cir.setReturnValue(new BlockPos(chunkPos.getOffsetX(rand.nextInt(16)), 0, chunkPos.getOffsetZ(rand.nextInt(16))));
        }
    }
}

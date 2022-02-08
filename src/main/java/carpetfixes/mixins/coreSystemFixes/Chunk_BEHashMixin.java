package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.gen.chunk.BlendingData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(Chunk.class)
public class Chunk_BEHashMixin {

    @Mutable
    @Final
    @Shadow
    protected Map<BlockPos, BlockEntity> blockEntities;


    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void reloadNewHashMap(ChunkPos pos, UpgradeData upgradeData, HeightLimitView heightLimitView, Registry biome, long inhabitedTime, ChunkSection[] sectionArrayInitializer, BlendingData blendingData, CallbackInfo ci) {
        if (CarpetFixesSettings.reloadUpdateOrderFix) blockEntities = new LinkedHashMap<>();
    }
}

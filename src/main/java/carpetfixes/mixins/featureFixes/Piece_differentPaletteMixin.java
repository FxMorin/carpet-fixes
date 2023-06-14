package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Shipwrecks are multiple pieces when split between chunks. Like all structures they use the same position for
 * each piece. Except that's actually not the case, the position passed to generate() in the Shipwreck piece
 * does not have the same y value for each piece. I will work on a fix for this later.
 * The secondary problem is that the shipwreck piece uses abstractRandom.nextInt(3) to know the offset on the
 * Y axis that it should have. Here's 2 reason why this is not great:
 * 1. The blockPos (including the new Y axis) is used to generate the random that will be used to pick the
 *    palette that the ship should use. Hence, causing different palettes using between 2 chunk borders<br>
 * 2. The abstractRandom can be used by multiple pieces of the same structure one after another. Which means that
 *    the next structure will have a different value for the Y axis.<br>
 * This is why you only ever see the bug when both halves are at different heights, since the height and the
 * palette are one in the same, they use the same random.
 * <br><br>
 * So how do we go about fixing this. Firstly, the structure should not receive different positions even in the Y
 * axis. That's just asking for trouble in the future, and also makes the fix slightly more complicated.
 * Secondly, you are going to want to use a positional random like you do for most others structure randoms, in
 * this fix I used that random `this.placementData.getRandom(pos)` if the position is the same, so will the random.
 * Although as mentioned before, the position is not the same, although it's very close. Since the ship cannot be
 * near another ship and checks to make sure its on the surface, we don't need to worry about 2 ships appearing at
 * the same position. So we set the Y value to 0 for the position that we use in the positional random.
 * This results in the randoms having the same values for all pieces!
 *
 * @author FX - PR0CESS
 */

@Mixin(net.minecraft.structure.ShipwreckGenerator.Piece.class)
public abstract class Piece_differentPaletteMixin extends SimpleStructurePiece {

    @Shadow
    @Final
    private boolean grounded;

    public Piece_differentPaletteMixin(StructurePieceType type, int length,
                                                          StructureTemplateManager structureTemplateManager,
                                                          Identifier id, String template,
                                                          StructurePlacementData placementData, BlockPos pos) {
        super(type, length, structureTemplateManager, id, template, placementData, pos);
    }


    @Inject(
            method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;" +
                    "Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;" +
                    "Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;" +
                    "Lnet/minecraft/util/math/BlockPos;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customBlockPos(StructureWorldAccess world, StructureAccessor structureAccessor,
                                ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox,
                                ChunkPos chunkPos, BlockPos pos, CallbackInfo ci) {
        if (CFSettings.shipwreckChunkBorderIssuesFix) {
            int topY = world.getTopY(), avgY = 0;
            Vec3i size = this.template.getSize();
            Heightmap.Type type = this.grounded ? Heightmap.Type.WORLD_SURFACE_WG : Heightmap.Type.OCEAN_FLOOR_WG;
            int surface = size.getX() * size.getZ();
            if (surface == 0) {
                avgY = world.getTopY(type, this.pos.getX(), this.pos.getZ());
            } else {
                BlockPos blockPos = this.pos.add(size.getX() - 1, 0, size.getZ() - 1);
                for(BlockPos blockPos2 : BlockPos.iterate(this.pos, blockPos)) {
                    int y = world.getTopY(type, blockPos2.getX(), blockPos2.getZ());
                    avgY += y;
                    topY = Math.min(topY, y);
                }
                avgY /= surface;
            } // All changes to vanilla code are past this point
            if (this.grounded) { // Using BlockPos random in order to get the same results for each piece
                int offsetY = topY - size.getY() / 2 - this.placementData.getRandom(this.pos.withY(0)).nextInt(3);
                this.pos = this.pos.withY(offsetY); // yuck - new BlockPos(this.pos.getX(), m, this.pos.getZ());
            } else {
                this.pos = this.pos.withY(avgY);
            }
            super.generate(world, structureAccessor, chunkGenerator, random, chunkBox, chunkPos, pos);
            ci.cancel();
        }
    }
}

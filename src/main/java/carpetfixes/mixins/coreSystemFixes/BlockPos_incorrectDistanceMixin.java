package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockPos.class)
public abstract class BlockPos_incorrectDistanceMixin extends Vec3i {

    public BlockPos_incorrectDistanceMixin(int x, int y, int z) {super(x, y, z);}

    /**
     * Thanks to 2No2Name for noticing that `BlockPos.ORIGIN.getSquaredDistance(BlockPos.ORIGIN)`
     * returns 0.75 xD. This is due to `getSquaredDistance` automatically setting `treatAsBlockPos`
     * to true, which in this case shouldn't happen since it's already a BlockPos.
     *
     * The fix is simply to override getSquaredDistance in BlockPos so `treatAsBlockPos` is set to false.
     *
     * This method was not intended to be used against 2 blockPos, although Mojang does it... So we need to fix it.
     */

    /*
    THIS FIX POSSIBLY FIXES:
    - PoiManager.getInRange - Some blocks which are in range won't count as being in range. While others that are not supposed to count might make it in.
    - PoiManager.findAllClosestFirst - Does not actually get the closest first. Might be in the wrong order
    - PoiManager.findClosest - " "
    - PotentialCalculator.PointCharge.getPotentialChange - PotentialChange may not being correct
    - BlockBlobFeature.place - Blobs not being spheres & being smaller then they should be
    - GeodeFeature.place - Blocks not being set in the correct place
    - PortalForcer.findPortalAround - Portal linking may link up to portals that are further away
    - PortalForcer.createPortal - Portal placing location might be further then the closest location
    - SetClosestHomeAsWalkTarget.checkExtraStartConditions - Some valid positions are set as not valid due to being too far. Also some invalid position are close enough
    - MoveThroughVillageGoal.canUse - Sometimes gets the wrong block in the second check
    - MoveToTargetSink.tick - Some valid spots are considered not valid, some invalid spots are considered valid. Maybe be the cause of directional bias
    - PrepareRamNearestTarget.calculateRammingStartPosition - wrong starting position might be chosen
    - LongJumpToRandomPos.LongJumpToRandomPos - Wrong jump might be chosen
    - Raid.moveRaidCenterToNearbyVillageSection - Wrong Raid Center might be used
    - Bee.findNearbyHivesWithSpace - Favours negative coordinates & Might pick negative coordinate even if positive coordinate is closer
    - Mob.isWithinRestriction - Might return wrong value
    - LevelRenderer.initializeQueueForFullUpdate - Favours negative coordinates, picks some chunks with are not actually the closest
    - Raid.updateRaiders - Raids might leave raid if off by 1 block xD
    - GameEventListenerRenderer.isExpired - Might not expire at right time. xD
     */

    @Override
    public double getSquaredDistance(Vec3i vec) {
        return this.getSquaredDistance(vec.getX(), vec.getY(), vec.getZ(), !CarpetFixesSettings.incorrectBlockPosDistanceFix);
    }
}

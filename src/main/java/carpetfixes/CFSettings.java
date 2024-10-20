package carpetfixes;

import carpet.api.settings.Rule;
import carpetfixes.settings.Validators;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static carpet.api.settings.RuleCategory.*;
import static carpetfixes.helpers.RuleCategory.*;

public class CFSettings {

    //Add your name above the rules so people know who to contact about changing the code. E.x. By FX - PR0CESS

    // Global Variables
    public static boolean scheduleWorldBorderReset = false;
    public static boolean useCustomRedstoneUpdates = false;
    public static final ThreadLocal<Set<BlockPos>> LAST_DIRT = ThreadLocal.withInitial(HashSet::new);
    public static final Predicate<BlockState> IS_REPLACEABLE = AbstractBlock.AbstractBlockState::isReplaceable;
    public static final ThreadLocal<Boolean> IS_TICK_SAVE = ThreadLocal.withInitial(() -> false);

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean crystalExplodeOnExplodedFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean spongeUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Multiple Updates
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean hopperUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean observerUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean targetBlockUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean blockCollisionCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.blockUpdateOrderValidator.class,
            categories = {BUGFIX}
    )
    public static boolean blockUpdateOrderFix = false;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.enableCustomRedstoneRuleValidator.class,
            categories = {BUGFIX}
    )
    public static boolean extendedBlockUpdateOrderFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean comparatorUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean incorrectBounceLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean incorrectBubbleColumnLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CLIENT}
    )
    public static boolean directionalBlockSlowdownFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean petsBreakLeadsDuringReloadFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean endermanDontUpdateOnPlaceFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, VANILLA}
    )
    public static boolean endermanUselessMinecartTeleportingFix = false;

    //by B14CK313
    @Rule(
            categories = {BUGFIX, VANILLA}
    )
    public static boolean endermanDontTakeExplosionDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean railInvalidUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean railMissingUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean railMissingUpdateAfterPushFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, OPTIMIZATION}
    )
    public static boolean endVoidRingsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean sleepingDelaysFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tntCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean fallingBlocksCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    //Marked as vanilla since it does not change any behaviour, just keeps spawn chunks loaded
    @Rule(
            categories = {BUGFIX, VANILLA}
    )
    public static boolean spawnChunkEntitiesUnloadingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean repairCostItemNotStackingFix = false;

    //By Skyrising
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean drownedEnchantedTridentsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED, WONTFIX, VANILLA}
    )
    public static boolean witchHutsSpawnIncorrectCatFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean hardcodedSeaLevelFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean fishingOutsideWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean xpOrbCollisionFix = false;

    //by FX - PR0CESS
    //Currently only Slime, Mushroom, Zombie, Zombie Villager, & piglins are supported. More to come eventually when I stop being lazy
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean conversionFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean explosionBreaksItemFrameInWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean movingBlocksDestroyPathFix = false;

    //by FX - PR0CESS
    //My Bug on it: MC-232725
    @Rule(
            validators = Validators.WitherGolemSpawningFixValidator.class,
            categories = {BUGFIX, INTENDED, RECOMMENDED}
    )
    public static boolean witherGolemSpawningFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean illegalBreakingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean headlessPistonFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean sleepingResetsThunderFix = false;

    //code by FX - PR0CESS
    //solution by DawNemo
    @Rule(
            categories = {BUGFIX, RECOMMENDED, VANILLA}
    )
    public static boolean incorrectExplosionExposureFix = false;

    //by FX - PR0CESS
    //Recommended even thought its experimental since it does save a ton of performance
    //Marked as Vanilla since its very, very hard to run into a situation where it affects vanilla
    //Only technical players would be able to tell the difference if they really tried
    @Rule(
            validators = Validators.enableCustomRedstoneRuleValidator.class,
            categories = {BUGFIX, EXPERIMENTAL, RECOMMENDED, VANILLA, OPTIMIZATION}
    )
    public static boolean duplicateBlockUpdatesFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean trapdoorMissingUpdateFix = false;

    //by FX - PR0CESS
    //No bug report has been made about this bug yet
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean uselessDetectorRailUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean beeStuckInVoidFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean allayStuckInVoidFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean invulnerableEndCrystalFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean creeperPortalFuseResetsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean soulSpeedIncorrectDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean endCrystalPlacingTooEarlyFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean respawnDragonWithoutAllEndCrystalsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean treeTrunkLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            validators = Validators.enableCustomRedstoneRuleValidator.class,
            categories = {BUGFIX, RECOMMENDED, VANILLA, OPTIMIZATION}
    )
    public static boolean uselessSelfBlockUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean tntMinecartExplodesTwiceFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean breakAnythingDoorGoalFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean blackstoneButtonBreakSpeedFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean chestUsablePastWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, VANILLA}
    )
    public static boolean itemFramePlaysSoundOnReadFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, VANILLA, RECOMMENDED, CREATIVE}
    )
    public static boolean incorrectNbtChecks = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean endPortalRemovesEffectsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean inconsistentRedstoneTorchFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean foxesDropItemsWithLootOffFix = false;

    //by Jack
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean foxesDisregardPowderSnowFix = false;

    //by Jack
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean spectatorAdvancementGrantingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean instantFluidFlowingFix = false;

    //by FX - PR0CESS
    //Not a dupe fix, although it was written to prevent dupes in the future
    @Rule(
            categories = {BUGFIX, RECOMMENDED, DUPE}
    )
    public static boolean saferItemTransfers = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean beeNotLeavingHiveFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean hangingEntityTriggersTrapsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean boatsTakeFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean boatsDontTakeFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean buriedTreasureAlwaysCenterFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean reinforcementsOnlySpawnZombiesFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, INTENDED}
    )
    public static boolean voidKillsLoyaltyTridentsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean endermanLowerPiercingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean projectileNotDetectedOnPlaceFix = false;

    //by FX - PR0CESS
    //Might make it so no arrows bypass the totem, unsure yet
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean arrowEffectsBypassTotemsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean mobsConvertingWithoutBlocksFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean mobsIgnoreOwnerOnPickupFix = false;

    //by FX - PR0CESS
    @Rule(
            validators = Validators.enableCustomRedstoneRuleValidator.class,
            categories = {BUGFIX}
    )
    public static boolean redstoneComponentUpdateOrderOnBreakFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CLIENT}
    )
    public static boolean velocitySeparateAxisCancellingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean armorStandMissingFunctionalityFix = false;

    //by Adryd
    @Rule(
            categories = {BUGFIX, WONTFIX}
    )
    public static boolean endermanTeleportWithoutAIFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean redstoneRedirectionMissingUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean pistonsPushWaterloggedBlocksFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tileDropsAffectedByFloatingPointFix = false;

    //by FX - PR0CESS
    @Rule(
            validators = Validators.WorldBorderCollisionRoundingFixValidator.class,
            categories = {BUGFIX, INTENDED}
    )
    public static boolean worldBorderCollisionRoundingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean detectorRailOffsetUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean placeBlocksOutsideWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean incorrectPistonWorldBorderCheckFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean explosionsBypassWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean spawnEggOffsetEventFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, INTENDED}
    )
    public static boolean spawnEggMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean spawnEggMissingEventFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean villagerDiscountIgnoresOfflinePlayersFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean villagerToWitchBedOccupiedFix = false;

    //by FX - PR0CESS, ported from carpetmod112 (By Xcom)
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean reloadUpdateOrderFix = false;

    //by FX - PR0CESS, fix originally by Xcom
    @Rule(
            categories = {BUGFIX, NBT}
    )
    public static boolean pistonReloadInconsistencyFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean armorStandNegateLavaDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean armorStandNegateCactusDamageFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean armorStandNegateAnvilDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tripwireNotDisarmingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, INTENDED}
    )
    public static boolean powderedSnowOpacityFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CLIENT, RECOMMENDED}
    )
    public static boolean invisibleHopperFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, NBT}
    )
    public static boolean blueWitherSkullNotSavedFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CRASHFIX, RECOMMENDED}
    )
    public static boolean nocomExploitFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CREATIVE}
    )
    public static boolean structureManagerCantLoadSnbtFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean nonSolidBlocksBreakCactusIfPushedFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean mobsSpawnOnMovingPistonsFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean geodeLavalogFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean frictionlessEntitiesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean mobsTargetDeadEntitiesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean oresDontDropXpWhenBlownUpFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean entityGrowingUpCollisionClippingFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean slowedEntityGoalsFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean pistonUpdateOrderIsLocationalFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean missingObserverUpdatesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean eatCakeFromAllSidesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL, INTENDED}
    )
    public static boolean unableToModifyPlayerDataFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tradeDemandDecreasesIndefinitelyFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean voidTradingFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean lavaCalculatesWrongFireStateFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean powderSnowOnlySlowIfFeetInBlockFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean brokenHiddenStatusEffectFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean absorptionStaysWithoutHeartsFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean zeroDamageHurtsWolvesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean noteBlockNotPoweredOnPlaceFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean shipwreckChunkBorderIssuesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean doorBreakNotStoppedOnDeathFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean projectileKeepsVelocityFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean giantTreesHaveExtraLogFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean duplicateEntityUUIDFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean iceWaterSkipsWaterloggedFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean witchAndCatSpawnMergedFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean hoppersSelectMinecartsRandomlyFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean beesSwimInWaterAndDieFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean hungerGoesDownInPeacefulFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean crossDimensionTeleportLosesStatsFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean fallingBlockDamageIsOffsetFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean beaconsAlwaysPlaySoundOnBreakFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean markerArmorStandsTriggerBlocksFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean markerArmorStandsCreateBubblesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean armorStandsCantRideVehiclesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean boatTooFarFromDispenserFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CREATIVE}
    )
    public static boolean creativeEnchantingCostsExperienceFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean ocelotsAndCatsTryToFleeInVehicleFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean sheepEatGrassThroughBlocksFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean snowmanCreateSnowWhileFloatingFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean leashKnotNotUpdatingOnBreakFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean bucketableMobsNotDetachingLeashesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean followParentGoalBreaksMovementFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean beesDropLikeBouldersFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean pistonsPushTooFarFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean convertConvertingZombieVillagersFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean beesFearRainFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean mobsAttackThroughBlocksFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean selfHarmFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean doubleBlocksSkipEntityCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean dripstoneSkipsEntityCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean recordWorldEventFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean bedTeleportExploitFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean viewerCountNegativesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean shulkerBoxMissingUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean snowGolemAttackCreepersFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean sweepingIgnoresFireAspectFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean spawnInsideGatewayFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean enderDragonDoesntDropBlocksFix = false;

    //By Max Henkel, & FX
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean inhabitedTimeFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean badVillagerPyrotechnicsFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean endermanPainfulTeleportFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean endermanAvoidProjectilesInVehicleFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, PARITY}
    )
    public static boolean comparatorTransparencyFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean daylightSensorPlacementFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tridentFallingDamageFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tntMinecartTerribleCollisionFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tntExtendedHitboxClipFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean brewingResetsOnUnloadFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean comparatorSkipsBlockedChestFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean chorusFruitFallDamageFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean potionEffectsAffectDeadEntitiesFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean obsidianPlatformDestroysBlocksFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CLIENT}
    )
    public static boolean depthStriderSlowsRiptideFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean teleportPastWorldBorderFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean grassSnowLayersFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean lavaIgnoresBubbleColumnFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean directionalMinecartCollisionFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean minecartWontBounceFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean detectorRailDetectsTooEarlyFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean detectorRailsDontPowerDiagonallyFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean reinforcementsSpawnOffCenteredFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean turtleEggWrongCollisionCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean pointedDripstoneWrongCollisionFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean bedLandingWrongCollisionFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, MODDED}
    )
    public static boolean paletteCopyDataCorruptionFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, MODDED}
    )
    public static boolean pistonsPushEntitiesBehindThemFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean tallGrassWaterWontSpreadFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean enderpearlClipFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean wardenEatsArmorFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean endCrystalsOnPushDontSummonDragonFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, MODDED}
    )
    public static boolean sitGoalAlwaysResettingFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {EXPERIMENTAL}
    )
    public static boolean simulatedOutOfMemoryCrashFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {EXPERIMENTAL}
    )
    public static boolean someUpdatesDontCatchExceptionsFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean fallingBlocksCantReuseGatewaysFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, CRASHFIX, RECOMMENDED}
    )
    public static boolean villagersDontReleaseMemoryFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean structuresIgnorePassengersFix = false;

    @Rule(
            categories = {BUGFIX, RECOMMENDED}
    )
    public static boolean locateCommandDistanceFix = false;

    /*

    OPTIMIZATIONS
    These are all the rules that where made to optimize the game. Rules that are considered optimizations
    but that where not intended to be optimizations are not put in this category.

     */

    //by FX - PR0CESS
    @Rule(
            categories = {OPTIMIZATION, VANILLA, CLIENT, RECOMMENDED}
    )
    public static boolean optimizedBiomeAccess = false;

    //by FX - PR0CESS
    @Rule(
            categories = {OPTIMIZATION, VANILLA, CLIENT, RECOMMENDED}
    )
    public static boolean optimizedRecipeManager = false;

    //By Hilligans
    @Rule(
            validators = Validators.optimizedNeighborUpdaterValidator.class,
            categories = {OPTIMIZATION, VANILLA, EXPERIMENTAL}
    )
    public static boolean optimizedNeighborUpdater = false;


    /*

    DUPE BUGS
    Bugs that dupe stuff. This is the cool bug category. They are just like all the
    other bugs, except they deserve a category of their own (for sorting purposes...)

     */

    //By FX - PR0CESS
    @Rule(
            categories = {BUGFIX, DUPE}
    )
    public static boolean fallingBlockDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            categories = {BUGFIX, DUPE}
    )
    public static boolean railDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            categories = {BUGFIX, DUPE}
    )
    public static boolean pistonDupingFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, RECOMMENDED, DUPE}
    )
    public static boolean giveCommandDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL, DUPE}
    )
    public static boolean beeDupeFix = false;

    // by apple502j
    @Rule(
            categories = {BUGFIX, RECOMMENDED, DUPE}
    )
    public static boolean breakSwapGeneralItemDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, DUPE}
    )
    public static boolean stringDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, DUPE}
    )
    public static boolean tripwireHookDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            categories = {BUGFIX, DUPE}
    )
    public static boolean lecternBlockDupeFix = false;


    /*

    RE-INTRODUCE
    Bugs that are no longer Unresolved that we reintroduce into the game
    or Bugs that where fixed in the snapshots that we re-introduce from older versions
    or just re-introducing rules/mechanics because we want to (usually performance)

    Damn these are some long rule names

     */

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceTargetBlockPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceLightningRodPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE, DUPE}
    )
    public static boolean reIntroducePortalGeneralItemDupe = false;

    //By whoImT from carpet-addons
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceFlintAndSteelBehavior = false;

    //By whoImT from carpet-addons
    @Rule(
            categories = {REINTRODUCE, EXPERIMENTAL, DUPE}
    )
    public static boolean reIntroduceDonkeyRidingDupe = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE, DUPE}
    )
    public static boolean reIntroduceItemShadowing = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceZeroTickFarms = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceOnlyAutoSaveSaving = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceVeryAggressiveSaving = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceFallingBlockEntityPhase = false;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.reIntroduceInstantBlockUpdatesValidator.class,
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceInstantBlockUpdates = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceProperGoalTimings = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroducePistonTranslocation = false;

    //By FX - PR0CESS
    @Rule(
            categories = {REINTRODUCE}
    )
    public static boolean reIntroduceTrapdoorUpdateSkipping = false;


    /*

    FABRIC-CARPET & CARPET-EXTRA OVERRIDES
    I want to move these rules over to carpet-fixes

     */

    //By FX - PR0CESS from fabric-carpet
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean lightningKillsDropsFix = false;

    //By DeadlyMC from carpet-extra
    @Rule(
            categories = {BUGFIX, EXPERIMENTAL}
    )
    public static boolean doubleRetraction = false;

    //Original By DeadlyMC (from carpet-extra), Fixed By FX - PR0CESS
    //Fixed bug in carpet-extra's implementation
    @Rule(
            categories = {BUGFIX}
    )
    public static boolean repeaterPriorityFix = false;

    //By FX - PR0CESS
    @Rule(
            categories = {EXPERIMENTAL}
    )
    public static boolean updateSuppressionCrashFix = false;


    /*

    DEBUG CONFIGURATIONS
    Debugging tools to help you find issues & bugs, but also to have fun ;)

     */

    //By FX - PR0CESS
    @Rule(
            categories = {DEBUG}
    )
    public static boolean debugStackDepth = false;

    @Rule(
            categories = {DEBUG}
    )
    public static boolean debugStackTrace = false;

    @Rule(
            categories = {DEBUG}
    )
    public static boolean debugSimulatedOutOfMemory = false;


    /*

    ADVANCED CONFIGURATIONS
    Simple options that you can turn on and off are great, its one of the things I try to
    do. Since it makes it much easier for the end user to use the mod. These options are
    for more advanced users who want some more flexibility!

     */

    //By FX - PR0CESS
    @Rule(
            options = {"300", "200", "100", "50"},
            categories = {ADVANCED, OPTIMIZATION}
    )
    public static int maxChunksSavedPerAutoSave = 200;

    //By FX - PR0CESS
    @Rule(
            options = {"100", "50", "20", "10"},
            categories = {ADVANCED, OPTIMIZATION}
    )
    public static int maxChunksSavedPerTick = 20;

    //By FX - PR0CESS
    @Rule(
            options = {"120000", "60000", "10000", "1000", "0"},
            categories = {ADVANCED, OPTIMIZATION}
    )
    public static int chunkSaveCooldownDelay = 10000;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.onlineModeValidator.class,
            categories = {ADVANCED, EXPERIMENTAL}
    )
    public static boolean toggleOnlineMode = true;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.preventProxyConnectionsValidator.class,
            categories = {ADVANCED, EXPERIMENTAL}
    )
    public static boolean togglePreventProxyConnections = false;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.pvpEnabledValidator.class,
            categories = {ADVANCED, EXPERIMENTAL}
    )
    public static boolean togglePvpEnabled = true;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.flightEnabledValidator.class,
            categories = {ADVANCED, EXPERIMENTAL}
    )
    public static boolean toggleFlightEnabled = false;

    //By FX - PR0CESS
    @Rule(
            validators = Validators.enforceWhitelistValidator.class,
            categories = {ADVANCED, EXPERIMENTAL}
    )
    public static boolean toggleEnforceWhitelist = false;


    /*

    PARITY
    Parity bugs between java & bedrock edition. This category is basically a meme,
    we probably don't want these to actually be added to java!

     */

    //By FX - PR0CESS
    @Rule(
            validators = {
                    Validators.parityRandomBlockUpdatesValidator.class,
                    Validators.enableCustomRedstoneRuleValidator.class
            },
            categories = {PARITY}
    )
    public static boolean parityRandomBlockUpdates = false;

    //By FX - PR0CESS
    @Rule(
            categories = {PARITY}
    )
    public static boolean parityMovableLightBlocks = false;

    //By FX - PR0CESS
    @Rule(
            categories = {PARITY}
    )
    public static boolean parityTerribleComparators = false;
}

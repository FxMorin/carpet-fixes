package carpetfixes;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.*;
import static carpetfixes.helpers.RuleCategory.*;

public class CarpetFixesSettings {

    //Add your name above the rules so people know who to contact about changing the code. E.x. By FX - PR0CESS

    //By FX - PR0CESS
    @Rule(
            desc = "End Crystals now explode when damaged from explosions. End Crystal chaining",
            extra = "Fixes [MC-118429](https://bugs.mojang.com/browse/MC-118429)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean crystalExplodeOnExplodedFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Makes it so that sponges give block updates when absorbing water",
            extra = "Fixes [MC-220636](https://bugs.mojang.com/browse/MC-220636)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean spongeUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Multiple Updates
    @Rule(
            desc = "Makes it so that hoppers give block updates when placed while powered",
            extra = "Fixes https://www.youtube.com/watch?v=QVOONJ1OY44",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean hopperUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Make it so that observers give block updates when retracted and immediately repowered",
            extra = "Fixes [MC-136566](https://bugs.mojang.com/browse/MC-136566)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean observerUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Lots of Update
    @Rule(
            desc = "Fixes sticky piston heads not giving a block update when failing to pull slime",
            extra = "Fixes [MC-185572](https://bugs.mojang.com/browse/MC-185572)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean pistonPullingUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes StringTag Exploits due to StringTag writeUTF() not respecting readUTF() Limits causing crashes internally",
            extra = "Fixes ChunkRegen & [MC-134892](https://bugs.mojang.com/browse/MC-134892)",
            category = {CARPETFIXES,BUGFIX,CRASHFIX,RECOMMENDED,VANILLA}
    )
    public static boolean stringTagExploitFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect block collision checks for players",
            extra = "Fixes [MC-123364](https://bugs.mojang.com/browse/MC-123364)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean blockCollisionCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes falling blocks duping using the end portal",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean fallingBlockDuplicationFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes the issue where block updates are inconsistent due to directionality",
            extra = {"Warning! This changes how block updates are done and could effect some contraptions",
                    "Fixes [MC-161402](https://bugs.mojang.com/browse/MC-161402)"},
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean blockUpdateOrderFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes the issue where comparators don't always get updated correctly",
            extra = "Fixes [MC-120986](https://bugs.mojang.com/browse/MC-120986)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean comparatorUpdateFix = false;

    //by FX - PR0CESS
    //Recommended since it not only negates a crash but also tried to keep behaviour after it
    @Rule(
            desc = "Prevents update suppression from working. When the stack is reached, the following updates are moved to the next tick",
            category = {CARPETFIXES,BUGFIX,CRASHFIX,RECOMMENDED}
    )
    public static boolean updateSuppressionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some entities not bouncing on slime blocks and getting stuck",
            extra = "Fixes [MC-216985](https://bugs.mojang.com/browse/MC-216985)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean incorrectBounceLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some entities getting stuck in bubble columns",
            extra = "Fixes [MC-207866](https://bugs.mojang.com/browse/MC-207866)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean incorrectBubbleColumnLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes movement slowdown being calculated based on last block in search. Uses the slowest value instead",
            extra = "Fixes [MC-202654](https://bugs.mojang.com/browse/MC-202654)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean directionalBlockSlowdownFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes cats sometimes breaking there leads after giving a gift",
            extra = "Fixes [MC-202607](https://bugs.mojang.com/browse/MC-202607)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean catsBreakLeadsDuringGiftFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Leashed pets teleporting to the player when reloaded",
            extra = "Fixes [MC-173303](https://bugs.mojang.com/browse/MC-173303)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean petsBreakLeadsDuringReloadFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes enderman not updating the block they place correctly",
            extra = "Fixes [MC-183054](https://bugs.mojang.com/browse/MC-183054)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean endermanDontUpdateOnPlaceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes enderman constantly trying to teleport when in a minecart under daylight",
            extra = "Fixes [MC-227008](https://bugs.mojang.com/browse/MC-227008)",
            category = {CARPETFIXES,BUGFIX,VANILLA}
    )
    public static boolean endermanUselessMinecartTeleportingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails updating other rails before checking if they are in a valid location",
            extra = "Fixes [MC-174864](https://bugs.mojang.com/browse/MC-174864)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean railInvalidUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails on being moved, allowing for invalid states",
            extra = {"Prevents redstone budding from working","Fixes [MC-123311](https://bugs.mojang.com/browse/MC-123311)"},
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean railMissingUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails after being moved",
            extra = "Fixes [MC-96224](https://bugs.mojang.com/browse/MC-96224)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean railMissingUpdateAfterPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the bug which causes there to be void rings (empty chunks) in the end",
            extra = "Fixes [MC-159283](https://bugs.mojang.com/browse/MC-159283)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean endVoidRingsFix = false;

    //by FX - PR0CESS
    //Recommended since it just prevents the server from kicking you for not hacking
    @Rule(
            desc = "Fixes getting kicked for flying too long when jumping and riding an entity",
            extra = "Fixes [MC-98727](https://bugs.mojang.com/browse/MC-98727)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED,VANILLA}
    )
    public static boolean mountingFlyingTooLongFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fall damage being delayed by sleeping, fall damage will be removed instead",
            extra = "Fixes [MC-19830](https://bugs.mojang.com/browse/MC-19830)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean sleepingDelaysFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes TNT Entity not being able to go through nether portals",
            extra = "Fixes [MC-8983](https://bugs.mojang.com/browse/MC-8983)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean tntCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Falling Blocks not being able to go through nether portals",
            extra = "Fixes [MC-9644](https://bugs.mojang.com/browse/MC-9644)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean fallingBlocksCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    //Marked as vanilla since it does not change any behaviour, just keeps spawn chunks loaded
    @Rule(
            desc = "Fixes Spawn Chunks not ticking entities and block entities if no player online",
            extra = "Fixes [MC-59134](https://bugs.mojang.com/browse/MC-59134)",
            category = {CARPETFIXES,BUGFIX,VANILLA}
    )
    public static boolean spawnChunkEntitiesUnloadingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Named Blocks not stacking due to useless RepairCost tag",
            extra = "Fixes [MC-197473](https://bugs.mojang.com/browse/MC-197473)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean repairCostItemNotStackingFix = false;

    //By Skyrising
    @Rule(
            desc = "Makes enchantments work on tridents thrown by drowned",
            extra = "Fixes [MC-127321](https://bugs.mojang.com/browse/MC-127321)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean drownedEnchantedTridentsFix = false;

    //by FX - PR0CESS
    //Also fixes: MC-158154
    @Rule(
            desc = "Fixes multiple bugs related to effects happening only when player center in block instead of hitbox",
            extra = "Fixes [MC-1133](https://bugs.mojang.com/browse/MC-1133)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean playerBlockCollisionUsingCenterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect cat types spawning inside swamp huts",
            extra = "Fixes [MC-147659](https://bugs.mojang.com/browse/MC-147659)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED,VANILLA}
    )
    public static boolean witchHutsSpawnIncorrectCatFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect sea level height being used when datapacks change the sea height",
            extra = "Fixes [MC-226687](https://bugs.mojang.com/browse/MC-226687)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean hardcodedSeaLevelFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to fish outside of water",
            extra = "Fixes [MC-175544](https://bugs.mojang.com/browse/MC-175544)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean fishingOutsideWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to dupe items using the /give command",
            extra = "Fixes [MC-120507](https://bugs.mojang.com/browse/MC-120507)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean giveCommandDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes experience orbs acting as if flowing lava is a full block",
            extra = "Fixes [MC-226961](https://bugs.mojang.com/browse/MC-226961)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean xpOrbCollisionFix = false;

    //by FX - PR0CESS
    //Currently only Slime, Mushroom, Zombie, Zombie Villager, & piglins are supported. More to come eventually when I stop being lazy
    @Rule(
            desc = "Fixes mob multiple different mob conversions",
            extra = "Fixes [MC-88967](https://bugs.mojang.com/browse/MC-88967)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean conversionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Explosions being able to destroy item frames in water",
            extra = "Fixes [MC-3697](https://bugs.mojang.com/browse/MC-3697)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean explosionBreaksItemFrameInWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Moving Blocks from destroying path blocks",
            extra = "Fixes [MC-161026](https://bugs.mojang.com/browse/MC-161026)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean movingBlocksDestroyPathFix = false;

    //by FX - PR0CESS
    //My Bug on it: MC-232725
    @Rule(
            desc = "Fixes Withers and Golems not spawning due to replaceable blocks being in the way",
            extra = "Fixes [MC-60792](https://bugs.mojang.com/browse/MC-60792)",
            validate = WitherGolemSpawningFixValidator.class,
            category = {CARPETFIXES,BUGFIX,INTENDED,RECOMMENDED}
    )
    public static boolean witherGolemSpawningFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Breaking blocks that should not be able to be broken using headless pistons",
            extra = {"Illegal blocks are any blocks that have a hardness value of -1.0F",
                     "Fixes [MC-188220](https://bugs.mojang.com/browse/MC-188220)"},
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean illegalBreakingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make and use Headless Pistons",
            extra = "Fixes [MC-27056](https://bugs.mojang.com/browse/MC-27056)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean headlessPistonFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the rain timer being reset whenever players sleep",
            extra = "Fixes [MC-63340](https://bugs.mojang.com/browse/MC-63340)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean sleepingResetsRainFix = false;

    //by FX - PR0CESS
    /*@Rule(
            desc = "Fixes changing between spactator lowering your player",
            extra = "Fixes [MC-146582](https://bugs.mojang.com/browse/MC-146582)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean spectatorLowersPlayerFix = false;*/

    //code by FX - PR0CESS
    //solution by DawNemo
    @Rule(
            desc = "Fixes incorrect explosion exposure calculations",
            extra = "Fixes [MC-232355](https://bugs.mojang.com/browse/MC-232355)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED,VANILLA}
    )
    public static boolean incorrectExplosionExposureFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes /data duping inventories when modifying entity data",
            extra = "Fixes [MC-112826](https://bugs.mojang.com/browse/MC-112826) & [MC-191011](https://bugs.mojang.com/browse/MC-191011)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED,VANILLA}
    )
    public static boolean nbtDataDupeFix = false;

    //by FX - PR0CESS
    //Recommended even thought its experimental since it does save a ton of performance
    //Marked as Vanilla since its very very hard to run into a situation where it affects vanilla
    @Rule(
            desc = "Fixes some redstone components send duplicated block updates",
            extra = "Fixes [MC-231071](https://bugs.mojang.com/browse/MC-231071)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL,RECOMMENDED,VANILLA}
    )
    public static boolean duplicateBlockUpdatesFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some blocks not popping off when a trapdoor opens",
            extra = "Fixes [MC-157300](https://bugs.mojang.com/browse/MC-157300)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean trapdoorMissingUpdateFix = false;

    //by FX - PR0CESS
    //No bug report has been made about this bug yet
    @Rule(
            desc = "Fixes the detector rail giving useless comparator updates on entity collision",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean uselessDetectorRailUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees getting stuck in the void due to gravity being disabled",
            extra = "Fixes [MC-167279](https://bugs.mojang.com/browse/MC-167279)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean beeStuckInVoidFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees duplicating while trying to load a beehive/beenest in unloaded chunks",
            extra = "Fixes [MC-234471](https://bugs.mojang.com/browse/MC-234471)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean beeDupeFix = false;

    //by FX - PR0CESS
    /*@Rule(
            desc = "Fixes illegal/corrupt block entities from existing",
            extra = "Fixes [MC-234396](https://bugs.mojang.com/browse/MC-234396)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean illegalBlockEntityFix = false;*/

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make permanent invulnerable end crystals",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean invulnerableEndCrystalFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes signal strength being inaccurate and skipping odd signal strengths due to precision loss with distance",
            extra = "Fixes [MC-218222](https://bugs.mojang.com/browse/MC-218222)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean sculkSensorPrecisionLossFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes creepers resetting there fuse duration when travelling through a nether portal after being ignited by flint & steel",
            extra = "Fixes [MC-234754](https://bugs.mojang.com/browse/MC-234754)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean creeperPortalFuseResetsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes soul speed enchantment from damaging boots when riding a vehicle",
            extra = "Fixes [MC-200991](https://bugs.mojang.com/browse/MC-200991)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean soulSpeedIncorrectDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes placing end crystals too early doesn't resummon the Ender Dragon",
            extra = "Fixes [MC-215763](https://bugs.mojang.com/browse/MC-215763)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean endCrystalPlacingTooEarlyFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to respawn the ender dragon using only 2 ender crystals instead of the intended 4",
            extra = "Fixes [MC-102774](https://bugs.mojang.com/browse/MC-102774)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean respawnDragonWithoutAllEndCrystalsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes trees considering the bottom block as a trunk and modifying leaves because of it",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean treeTrunkLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes blocks using updateNeighbors() on blocks next to them, making itself get a block update even though it does not accept block updates",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED,VANILLA}
    )
    public static boolean uselessSelfBlockUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Tnt Minecarts being able to explode twice, killing their own drops",
            category = {CARPETFIXES,BUGFIX,VANILLA}
    )
    public static boolean tntMinecartExplodesTwiceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Zombies & Vindicators being able to break any block where a door was",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean breakAnythingDoorGoalFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Blackstone Buttons taking longer then other buttons to break",
            extra = "Fixes [MC-199752](https://bugs.mojang.com/browse/MC-199752)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean blackstoneButtonBreakSpeedFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Transparent blocks placed between bookshelves and enchanting tables negating bonuses received",
            extra = "Fixes [MC-2474](https://bugs.mojang.com/browse/MC-2474)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean transparentBlocksNegateEnchantingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes chests being accessible outside the world border by placing a chest near it, simply by preventing chests from merging at the world border",
            extra = "Fixes [MC-67844](https://bugs.mojang.com/browse/MC-67844)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean chestUsablePastWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to place half beds outside of the world border",
            extra = "Fixes [MC-117800](https://bugs.mojang.com/browse/MC-117800)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean bedsCanBeInWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes item frames playing a sound when they are read from nbt",
            extra = "Fixes [MC-123450](https://bugs.mojang.com/browse/MC-123450)",
            category = {CARPETFIXES,BUGFIX,VANILLA}
    )
    public static boolean itemFramePlaysSoundOnReadFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes commands not allowing all nbt tags to work correctly",
            extra = "Fixes [MC-112257](https://bugs.mojang.com/browse/MC-112257)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean incorrectNbtChecks = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the end portal removing your status effects when going from the overworld to the end",
            extra = "Fixes [MC-6431](https://bugs.mojang.com/browse/MC-6431)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean endPortalRemovesEffectsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes burnt-out redstone torches having inconsistent behavior for turning on again",
            extra = "Fixes [MC-120938](https://bugs.mojang.com/browse/MC-120938)",
            category = {CARPETFIXES,BUGFIX,RECOMMENDED}
    )
    public static boolean inconsistentRedstoneTorchFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes gamerule doMobLoot not effecting foxes from dropping their items",
            extra = "Fixes [MC-153010](https://bugs.mojang.com/browse/MC-153010)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean foxesDropItemsWithLootOffFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes saved worlds corrupting due to missing structures",
            extra = "Fixes [MC-194811](https://bugs.mojang.com/browse/MC-194811)",
            category = {CARPETFIXES,BUGFIX,CRASHFIX,RECOMMENDED,VANILLA}
    )
    public static boolean missingStructureCorruptionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fluids being able to instantly flow!",
            extra = "Fixes [MC-215636](https://bugs.mojang.com/browse/MC-215636)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean instantFluidFlowingFix = false;

    /*

    BACKPORTS
    Bugs that are no longer Unresolved that we reintroduce into the game
    or Bugs that where fixed in the snapshots that we backport to older versions

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces target blocks being permanently powered when moved by pistons",
            extra = "Backports [MC-173244](https://bugs.mojang.com/browse/MC-173244)",
            category = {CARPETFIXES,BACKPORT}
    )
    public static boolean oldTargetBlockPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces lightning rods being permanently powered when moved by pistons",
            extra = "Backports [MC-203718](https://bugs.mojang.com/browse/MC-203718)",
            category = {CARPETFIXES,BACKPORT}
    )
    public static boolean oldLightningRodPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces general item dupe using dolphins, and some other dimension change dupes",
            category = {CARPETFIXES,BACKPORT}
    )
    public static boolean oldPortalGeneralItemDupe = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces 1.12 flint and steel behavior. Flint and steel can be used for updating observers / buds",
            extra = "Backports [MC-4923](https://bugs.mojang.com/browse/MC-4923) from 18w05a",
            category = {CARPETFIXES,BUGFIX,BACKPORT}
    )
    public static boolean oldFlintAndSteelBehavior = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces multiplayer donkey/llama dupe bug based on disconnecting while riding donkey/llama",
            extra = {"Backports [MC-181241](https://bugs.mojang.com/browse/MC-181241) from 18w05a","This has not been tested in 1.17 and may not work!"},
            category = {CARPETFIXES,BUGFIX,BACKPORT,EXPERIMENTAL}
    )
    public static boolean oldDonkeyRidingDupe = false;

    /*

    FABRIC-CARPET OVERRIDES
    I want to move these rules over to carpet-fixes

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Lightning kills the items that drop when lightning kills an entity",
            extra = {"Setting to true will prevent lightning from killing drops",
                    "Fixes [MC-206922](https://bugs.mojang.com/browse/MC-206922)"},
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean lightningKillsDropsFix = false;


    /*

    CARPET-EXTRA OVERRIDES
    I want to move these rules over to carpet-fixes

     */

    //By DeadlyMC
    @Rule(
            desc = "Re-adds 1.8 double retraction to pistons",
            category = {CARPETFIXES, BUGFIX, EXPERIMENTAL},
            extra = { "Gives pistons the ability to double retract without side effects",
                    "Fixes [MC-88959](https://bugs.mojang.com/browse/MC-88959)" }
    )
    public static boolean doubleRetraction = false;

    //Original By DeadlyMC, Tweak By FX - PR0CESS
    //Fixed bug in carpet-extra implementation
    @Rule(
            desc = "Quick pulses won't get lost in repeater setups",
            extra = {"Probably brings back pre 1.8 behaviour.",
                    "Fixes [MC-54711](https://bugs.mojang.com/browse/MC-54711)"},
            category = {CARPETFIXES, BUGFIX}
    )
    public static boolean repeaterPriorityFix = false;


    /*

    FROM OTHER CARPET EXTENSIONS

    */

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            desc = "Fixes rails duplicating",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean railDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            desc = "Disable TNT, carpet and part of rail dupers",
            extra = {
                    "Attachment block update based dupers will do nothing and redstone component update based dupers can no longer keep their duped block",
                    "Implementation by Carpet-TIS-Addition - Dupe bad dig good"
            },
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean pistonDupingFix = false;


    /*

    VALIDATORS

    */
    private static class WitherGolemSpawningFixValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            ((CarvedPumpkinBlock)(Blocks.CARVED_PUMPKIN)).ironGolemPattern = null;
            ((CarvedPumpkinBlock)(Blocks.CARVED_PUMPKIN)).ironGolemDispenserPattern = null;
            return newValue;
        }
    }
}

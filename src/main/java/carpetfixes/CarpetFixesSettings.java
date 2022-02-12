package carpetfixes;

import carpet.CarpetServer;
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
            desc = "Fixes End Crystals not exploding when damaged from explosions. End Crystal chaining",
            extra = "[MC-118429](https://bugs.mojang.com/browse/MC-118429)",
            category = BUGFIX
    )
    public static boolean crystalExplodeOnExplodedFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes sponges not giving block updates when absorbing water",
            extra = "[MC-220636](https://bugs.mojang.com/browse/MC-220636)",
            category = BUGFIX
    )
    public static boolean spongeUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Multiple Updates
    @Rule(
            desc = "Fixes hoppers not giving block updates when placed while powered",
            extra = {"invisibleHopperFix is automatically enabled when used",
                    "(Youtube Video)[https://www.youtube.com/watch?v=QVOONJ1OY44]"},
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean hopperUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes observers not giving block updates when retracted and immediately repowered",
            extra = "[MC-136566](https://bugs.mojang.com/browse/MC-136566)",
            category = BUGFIX
    )
    public static boolean observerUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Lots of Update
    //Still requires more testing to perfect
    /*@Rule(
            desc = "Fixes sticky piston heads not giving a block update when failing to pull slime",
            extra = "[MC-185572](https://bugs.mojang.com/browse/MC-185572)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean pistonPullingUpdateFix = false;*/

    //By FX - PR0CESS
    //Not putting it in the dupe fix category since technically there is no way to dupe with it anymore. Although there probably will be
    @Rule(
            desc = "Fixes StringTag Exploits due to StringTag writeUTF() not respecting readUTF() Limits causing crashes internally",
            extra = {"This was the cause of ChunkRegen, and the book dupe. Both patched, although StringTag is still broken",
                    "[MC-134892](https://bugs.mojang.com/browse/MC-134892)"},
            category = {BUGFIX,CRASHFIX,RECOMMENDED,VANILLA,DUPE}
    )
    public static boolean stringTagExploitFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect block collision checks for players",
            extra = {"This bug allows you to teleport to the end at your overworld position, amongst many other problems",
                    "[MC-123364](https://bugs.mojang.com/browse/MC-123364)"},
            category = BUGFIX
    )
    public static boolean blockCollisionCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes issues where block updates are directional. Changes block update order from XYZ to XZY",
            extra = {"Warning! This changes how block updates are done and could effect some contraptions",
                    "[MC-161402](https://bugs.mojang.com/browse/MC-161402)"},
            category = BUGFIX
    )
    public static boolean blockUpdateOrderFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes the issue where comparators don't always get updated correctly",
            extra = "[MC-120986](https://bugs.mojang.com/browse/MC-120986)",
            category = BUGFIX
    )
    public static boolean comparatorUpdateFix = false;

    //by FX - PR0CESS
    //Recommended since it not only negates a crash but also tried to keep behaviour after it. Technically it's a dupe fix, although its a lot more than that
    @Rule(
            desc = "Prevents update suppression from working. When the stack is reached, the following updates are moved to the next tick",
            extra = "This does not prevent stack overflow exploits, it simple makes sure to update block after",
            category = {BUGFIX,CRASHFIX,RECOMMENDED,DUPE}
    )
    public static boolean updateSuppressionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some entities not bouncing on slime blocks and getting stuck",
            extra = "[MC-216985](https://bugs.mojang.com/browse/MC-216985)",
            category = {BUGFIX,EXPERIMENTAL}
    )
    public static boolean incorrectBounceLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some entities getting stuck in bubble columns",
            extra = "[MC-207866](https://bugs.mojang.com/browse/MC-207866)",
            category = {BUGFIX,EXPERIMENTAL}
    )
    public static boolean incorrectBubbleColumnLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes movement slowdown being calculated based on last block in search. Uses the slowest value instead",
            extra = "[MC-202654](https://bugs.mojang.com/browse/MC-202654)",
            category = BUGFIX
    )
    public static boolean directionalBlockSlowdownFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes cats sometimes breaking there leads after giving a gift",
            extra = "[MC-202607](https://bugs.mojang.com/browse/MC-202607)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean catsBreakLeadsDuringGiftFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Leashed pets teleporting to the player when reloaded, breaking there leads",
            extra = "[MC-173303](https://bugs.mojang.com/browse/MC-173303)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean petsBreakLeadsDuringReloadFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes enderman not updating the block they place correctly",
            extra = {"Only applies if you use datapacks or mods. E.x. Enderman placing a wither skull will not spawn a wither",
                    "[MC-183054](https://bugs.mojang.com/browse/MC-183054)"},
            category = {BUGFIX,EXPERIMENTAL}
    )
    public static boolean endermanDontUpdateOnPlaceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes enderman constantly trying to teleport when in a minecart under daylight",
            extra = "[MC-227008](https://bugs.mojang.com/browse/MC-227008)",
            category = {BUGFIX,VANILLA}
    )
    public static boolean endermanUselessMinecartTeleportingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails updating other rails before checking if they are in a valid location",
            extra = "[MC-174864](https://bugs.mojang.com/browse/MC-174864)",
            category = BUGFIX
    )
    public static boolean railInvalidUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails on being moved, allowing for invalid states",
            extra = {"Prevents redstone budding from working",
                    "[MC-123311](https://bugs.mojang.com/browse/MC-123311)"},
            category = BUGFIX
    )
    public static boolean railMissingUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails after being moved",
            extra = "[MC-96224](https://bugs.mojang.com/browse/MC-96224)",
            category = BUGFIX
    )
    public static boolean railMissingUpdateAfterPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the void rings in the end, which is a floating point error",
            extra = "[MC-159283](https://bugs.mojang.com/browse/MC-159283)",
            category = {BUGFIX,OPTIMIZATION}
    )
    public static boolean endVoidRingsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fall damage being delayed by sleeping, fall damage will be removed instead",
            extra = "[MC-19830](https://bugs.mojang.com/browse/MC-19830)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean sleepingDelaysFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes TNT Entity not being able to go through nether portals",
            extra = "[MC-8983](https://bugs.mojang.com/browse/MC-8983)",
            category = BUGFIX
    )
    public static boolean tntCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Falling Blocks not being able to go through nether portals",
            extra = "[MC-9644](https://bugs.mojang.com/browse/MC-9644)",
            category = BUGFIX
    )
    public static boolean fallingBlocksCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    //Marked as vanilla since it does not change any behaviour, just keeps spawn chunks loaded
    @Rule(
            desc = "Fixes Spawn Chunks not ticking entities and block entities if no player online",
            extra = "[MC-59134](https://bugs.mojang.com/browse/MC-59134)",
            category = {BUGFIX,VANILLA}
    )
    public static boolean spawnChunkEntitiesUnloadingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Named Blocks not stacking due to useless RepairCost tag",
            extra = "[MC-197473](https://bugs.mojang.com/browse/MC-197473)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean repairCostItemNotStackingFix = false;

    //By Skyrising
    @Rule(
            desc = "Fixes enchantments not working on tridents if thrown by a drowned",
            extra = "[MC-127321](https://bugs.mojang.com/browse/MC-127321)",
            category = BUGFIX
    )
    public static boolean drownedEnchantedTridentsFix = false;

    //by FX - PR0CESS
    //Also fixes: MC-158154
    @Rule(
            desc = "Fixes multiple bugs related to effects happening only when player center in block instead of hitbox",
            extra = "[MC-1133](https://bugs.mojang.com/browse/MC-1133)",
            category = BUGFIX
    )
    public static boolean playerBlockCollisionUsingCenterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect cat types spawning inside swamp huts",
            extra = "[MC-147659](https://bugs.mojang.com/browse/MC-147659)",
            category = {BUGFIX,RECOMMENDED,WONTFIX,VANILLA}
    )
    public static boolean witchHutsSpawnIncorrectCatFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect sea level height being used when datapacks change the sea height",
            extra = "[MC-226687](https://bugs.mojang.com/browse/MC-226687)",
            category = BUGFIX
    )
    public static boolean hardcodedSeaLevelFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to fish outside of water",
            extra = "[MC-175544](https://bugs.mojang.com/browse/MC-175544)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean fishingOutsideWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes experience orbs treating flowing lava as a full block",
            extra = "[MC-226961](https://bugs.mojang.com/browse/MC-226961)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean xpOrbCollisionFix = false;

    //by FX - PR0CESS
    //Currently only Slime, Mushroom, Zombie, Zombie Villager, & piglins are supported. More to come eventually when I stop being lazy
    @Rule(
            desc = "Fixes multiple different mob conversions, not transferring all the correct nbt",
            extra = "[MC-88967](https://bugs.mojang.com/browse/MC-88967)",
            category = {BUGFIX,EXPERIMENTAL}
    )
    public static boolean conversionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Explosions being able to destroy item frames in water",
            extra = "[MC-3697](https://bugs.mojang.com/browse/MC-3697)",
            category = BUGFIX
    )
    public static boolean explosionBreaksItemFrameInWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Moving Blocks from destroying path blocks",
            extra = "[MC-161026](https://bugs.mojang.com/browse/MC-161026)",
            category = BUGFIX
    )
    public static boolean movingBlocksDestroyPathFix = false;

    //by FX - PR0CESS
    //My Bug on it: MC-232725
    @Rule(
            desc = "Fixes Withers and Golems not spawning due to replaceable blocks being in the way",
            extra = "[MC-60792](https://bugs.mojang.com/browse/MC-60792)",
            validate = WitherGolemSpawningFixValidator.class,
            category = {BUGFIX,INTENDED,RECOMMENDED}
    )
    public static boolean witherGolemSpawningFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Breaking blocks that should not be able to be broken using headless pistons",
            extra = {"Illegal blocks are any blocks that have a hardness value of -1.0F",
                     "[MC-188220](https://bugs.mojang.com/browse/MC-188220)"},
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean illegalBreakingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make and use Headless Pistons",
            extra = "[MC-27056](https://bugs.mojang.com/browse/MC-27056)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean headlessPistonFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the thunder timer being reset whenever players sleep",
            category = BUGFIX
    )
    public static boolean sleepingResetsThunderFix = false;

    //by FX - PR0CESS
    /*@Rule(
            desc = "Fixes changing between spactator lowering your player",
            extra = "[MC-146582](https://bugs.mojang.com/browse/MC-146582)",
            category = BUGFIX
    )
    public static boolean spectatorLowersPlayerFix = false;*/

    //code by FX - PR0CESS
    //solution by DawNemo
    @Rule(
            desc = "Fixes incorrect explosion exposure calculations",
            extra = "[MC-232355](https://bugs.mojang.com/browse/MC-232355)",
            category = {BUGFIX,RECOMMENDED,VANILLA}
    )
    public static boolean incorrectExplosionExposureFix = false;

    //by FX - PR0CESS
    //Recommended even thought its experimental since it does save a ton of performance
    //Marked as Vanilla since its very, very hard to run into a situation where it affects vanilla
    //Only technical players would be able to tell the difference if they really tried
    @Rule(
            desc = "Fixes some redstone components sending duplicated block updates",
            extra = "[MC-231071](https://bugs.mojang.com/browse/MC-231071)",
            category = {BUGFIX,EXPERIMENTAL,RECOMMENDED,VANILLA,OPTIMIZATION}
    )
    public static boolean duplicateBlockUpdatesFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some blocks not popping off when a trapdoor opens",
            extra = "[MC-157300](https://bugs.mojang.com/browse/MC-157300)",
            category = BUGFIX
    )
    public static boolean trapdoorMissingUpdateFix = false;

    //by FX - PR0CESS
    //No bug report has been made about this bug yet
    @Rule(
            desc = "Fixes the detector rail giving useless comparator updates on entity collision",
            category = BUGFIX
    )
    public static boolean uselessDetectorRailUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees getting stuck in the void due to gravity being disabled",
            extra = "[MC-167279](https://bugs.mojang.com/browse/MC-167279)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean beeStuckInVoidFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make permanent invulnerable end crystals",
            category = {BUGFIX,EXPERIMENTAL}
    )
    public static boolean invulnerableEndCrystalFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes signal strength being inaccurate and skipping odd signal strengths due to precision loss with distance",
            extra = "[MC-218222](https://bugs.mojang.com/browse/MC-218222)",
            category = {BUGFIX,EXPERIMENTAL}
    )
    public static boolean sculkSensorPrecisionLossFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes creepers resetting there fuse duration when travelling through a nether portal after being ignited by flint & steel",
            extra = "[MC-234754](https://bugs.mojang.com/browse/MC-234754)",
            category = BUGFIX
    )
    public static boolean creeperPortalFuseResetsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes soul speed enchantment from damaging boots when riding a vehicle",
            extra = "[MC-200991](https://bugs.mojang.com/browse/MC-200991)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean soulSpeedIncorrectDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes placing end crystals too early not re-summoning the Ender Dragon",
            extra = "[MC-215763](https://bugs.mojang.com/browse/MC-215763)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean endCrystalPlacingTooEarlyFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to respawn the ender dragon using only 2 ender crystals instead of the intended 4",
            extra = "[MC-102774](https://bugs.mojang.com/browse/MC-102774)",
            category = BUGFIX
    )
    public static boolean respawnDragonWithoutAllEndCrystalsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes trees considering the bottom block as a trunk and modifying leaves because of it",
            category = BUGFIX
    )
    public static boolean treeTrunkLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes blocks using updateNeighbors() on blocks next to them, making itself get a block update even though it does not accept block updates",
            extra = "Some blocks do accept block updates, although we make sure to update them correctly",
            category = {BUGFIX,RECOMMENDED,VANILLA,OPTIMIZATION}
    )
    public static boolean uselessSelfBlockUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Tnt Minecarts being able to explode twice, killing their own drops",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean tntMinecartExplodesTwiceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Zombies & Vindicators being able to break any block where a door was",
            extra = "[MC-95467](https://bugs.mojang.com/browse/MC-95467)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean breakAnythingDoorGoalFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Blackstone Buttons taking longer then other buttons to break",
            extra = "[MC-199752](https://bugs.mojang.com/browse/MC-199752)",
            category = BUGFIX
    )
    public static boolean blackstoneButtonBreakSpeedFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Transparent blocks placed between bookshelves and enchanting tables negating bonuses received",
            extra = "[MC-2474](https://bugs.mojang.com/browse/MC-2474)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean transparentBlocksNegateEnchantingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes chests being accessible outside the world border by placing a chest near it",
            extra = {"We do this by preventing chests from merging with other chests outside of the world border",
                    "[MC-67844](https://bugs.mojang.com/browse/MC-67844)"},
            category = BUGFIX
    )
    public static boolean chestUsablePastWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes item frames playing a sound when they are read from nbt",
            extra = "[MC-123450](https://bugs.mojang.com/browse/MC-123450)",
            category = {BUGFIX,VANILLA}
    )
    public static boolean itemFramePlaysSoundOnReadFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes commands not allowing all nbt tags to work correctly",
            extra = "[MC-112257](https://bugs.mojang.com/browse/MC-112257)",
            category = {BUGFIX,VANILLA,RECOMMENDED,CREATIVE}
    )
    public static boolean incorrectNbtChecks = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the end portal removing your status effects when going from the overworld to the end",
            extra = "[MC-6431](https://bugs.mojang.com/browse/MC-6431)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean endPortalRemovesEffectsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes burnt-out redstone torches having inconsistent behavior for turning on again",
            extra = "[MC-120938](https://bugs.mojang.com/browse/MC-120938)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean inconsistentRedstoneTorchFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes gamerule doMobLoot not effecting foxes from dropping their items",
            extra = "[MC-153010](https://bugs.mojang.com/browse/MC-153010)",
            category = BUGFIX
    )
    public static boolean foxesDropItemsWithLootOffFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes saved worlds corrupting due to missing structures",
            extra = {"Make sure to always make a backup before using this, since its not been tested in the recent versions",
                    "[MC-194811](https://bugs.mojang.com/browse/MC-194811)"},
            category = {BUGFIX,CRASHFIX,EXPERIMENTAL,VANILLA}
    )
    public static boolean missingStructureCorruptionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fluids being able to instantly flow!",
            extra = "[MC-215636](https://bugs.mojang.com/browse/MC-215636)",
            category = BUGFIX
    )
    public static boolean instantFluidFlowingFix = false;

    //by FX - PR0CESS
    //Not a dupe fix, although it was written to prevent dupes in the future
    @Rule(
            desc = "Changes the code to use less copy calls, and instead pass references when possible",
            category = {BUGFIX,RECOMMENDED,DUPE}
    )
    public static boolean saferItemTransfers = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the sculk sensor having a directional bias with wool occlusion",
            extra = "[MC-207289](https://bugs.mojang.com/browse/MC-207289) & [MC-207635](https://bugs.mojang.com/browse/MC-207635)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean sculkSensorBiasFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees not leaving the hive in the end & nether due to weather conditions",
            extra = "[MC-168329](https://bugs.mojang.com/browse/MC-168329)",
            category = BUGFIX
    )
    public static boolean beeNotLeavingHiveFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes item frames & paintings being able to interact with pressure plates & tripwire hooks, causing them to not de-power",
            extra = "[MC-82055](https://bugs.mojang.com/browse/MC-82055)",
            category = BUGFIX
    )
    public static boolean hangingEntityTriggersTrapsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes boats breaking and giving fall damage under certain circumstances",
            extra = "[MC-119369](https://bugs.mojang.com/browse/MC-119369)",
            category = BUGFIX
    )
    public static boolean boatsTakeFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes boats not being destroyed by fall damage",
            extra = "[MC-98160](https://bugs.mojang.com/browse/MC-98160)",
            category = BUGFIX
    )
    public static boolean boatsDontTakeFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes buried treasure always generating in the center of a chunk",
            extra = "[MC-227443](https://bugs.mojang.com/browse/MC-227443)",
            category = BUGFIX
    )
    public static boolean buriedTreasureAlwaysCenterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes reinforcements only spawning zombies",
            extra = "[MC-14800](https://bugs.mojang.com/browse/MC-14800)",
            category = BUGFIX
    )
    public static boolean reinforcementsOnlySpawnZombiesFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect fall distance calculations causing fall distance to build up over time with leads",
            extra = "[MC-14167](https://bugs.mojang.com/browse/MC-14167)",
            category = BUGFIX
    )
    public static boolean incorrectFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the void killing loyalty tridents, even though they should come back to the player",
            extra = "[MC-125755](https://bugs.mojang.com/browse/MC-125755)",
            category = {BUGFIX,INTENDED}
    )
    public static boolean voidKillsLoyaltyTridentsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes piercing projectiles lowering there piercing when 'hitting' an enderman",
            extra = "[MC-145557](https://bugs.mojang.com/browse/MC-145557)",
            category = BUGFIX
    )
    public static boolean endermanLowerPiercingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes placing a button/pressure plate inside of a projectile not activating it",
            extra = "[MC-209284](https://bugs.mojang.com/browse/MC-209284)",
            category = BUGFIX
    )
    public static boolean projectileNotDetectedOnPlaceFix = false;

    //by FX - PR0CESS
    //Might make it so no arrows bypass the totem, unsure yet
    @Rule(
            desc = "Fixes arrows of harming bypassing Totems of Undying",
            extra = "[MC-206307](https://bugs.mojang.com/browse/MC-206307)",
            category = BUGFIX
    )
    public static boolean arrowEffectsBypassTotemsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes mobs continuing to convert even if the block is no longer there",
            extra = "[MC-227250](https://bugs.mojang.com/browse/MC-227250)",
            category = BUGFIX
    )
    public static boolean mobsConvertingWithoutBlocksFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Mobs ignoring 'Owner' when picking up loot",
            extra = "[MC-120578](https://bugs.mojang.com/browse/MC-120578)",
            category = BUGFIX
    )
    public static boolean mobsIgnoreOwnerOnPickupFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes redstone torch update order when being broken, causing unnatural updates",
            extra = "[MC-157644](https://bugs.mojang.com/browse/MC-157644)",
            category = BUGFIX
    )
    public static boolean redstoneTorchOrderOnBreakFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes falling blocks not behaving as expected when being teleported",
            extra = "[MC-151488](https://bugs.mojang.com/browse/MC-151488)",
            category = BUGFIX
    )
    public static boolean fallingBlockTeleportingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes player velocity on X and Z axis being cancelled separately at low values",
            extra = "[MC-241951](https://bugs.mojang.com/browse/MC-241951)",
            category = BUGFIX
    )
    public static boolean velocitySeparateAxisCancellingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes ArmorStands losing functionality due to 'optimizations'",
            extra = {"The marker tag will still prevent interaction though",
                    "[MC-244956](https://bugs.mojang.com/browse/MC-244956)"},
            category = BUGFIX
    )
    public static boolean armorStandMissingFunctionalityFix = false;

    //by Adryd
    @Rule(
            desc = "Fixes enderman teleporting when they have the NoAI tag",
            extra = "[MC-65668](https://bugs.mojang.com/browse/MC-65668)",
            category = {BUGFIX,WONTFIX}
    )
    public static boolean endermanTeleportWithoutAIFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes redstone redirection missing updates on redirecting",
            extra = "[MC-3703](https://bugs.mojang.com/browse/MC-3703)",
            category = BUGFIX
    )
    public static boolean redstoneRedirectionMissingUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to push waterlogged blocks while retaining the waterlogged state using a short pulse",
            extra = "[MC-130183](https://bugs.mojang.com/browse/MC-130183)",
            category = BUGFIX
    )
    public static boolean pistonsPushWaterloggedBlocksFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes tile drops at the world border spawning at the wrong location",
            extra = "Relates to [MC-4](https://bugs.mojang.com/browse/MC-4)",
            category = BUGFIX
    )
    public static boolean tileDropsAffectedByFloatingPointFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes players being able to crack there player seed",
            extra = {"This makes it so random is shared between all entities. Which is a good performance boost",
                    "recommended that you set this on permanently, and restart the server for best results"},
            category = {BUGFIX,OPTIMIZATION}
    )
    public static boolean entityRandomCrackingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes world border collision rounding to blocks for entity collisions",
            extra = "[MC-88482](https://bugs.mojang.com/browse/MC-88482) & [MC-247422](https://bugs.mojang.com/browse/MC-247422)",
            validate = WorldBorderCollisionRoundingFixValidator.class,
            category = {BUGFIX,INTENDED}
    )
    public static boolean worldBorderCollisionRoundingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes comparator signal ending prematurely due to offset block updates",
            extra = "[MC-247420](https://bugs.mojang.com/browse/MC-247420)",
            category = BUGFIX
    )
    public static boolean detectorRailOffsetUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to place blocks outside of the world border",
            extra = "[MC-63578](https://bugs.mojang.com/browse/MC-63578) & [MC-223613](https://bugs.mojang.com/browse/MC-223613)",
            category = BUGFIX
    )
    public static boolean placeBlocksOutsideWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes pistons being able to push blocks outside of the world border",
            extra = "[MC-82010](https://bugs.mojang.com/browse/MC-82010)",
            category = BUGFIX
    )
    public static boolean incorrectPistonWorldBorderCheckFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes explosions being able to destroy blocks outside of the world border",
            extra = "[MC-54606](https://bugs.mojang.com/browse/MC-54606)",
            category = BUGFIX
    )
    public static boolean explosionsBypassWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes players sending the STEP event before the HIT_GROUND event",
            extra = "[MC-247417](https://bugs.mojang.com/browse/MC-247417)",
            category = BUGFIX
    )
    public static boolean playerStepEventFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes projectiles sending the PROJECTILE_LAND event when landing on a vibration occluding block",
            extra = "[MC-208771](https://bugs.mojang.com/browse/MC-208771)",
            category = BUGFIX
    )
    public static boolean projectileMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes boats sending the SPLASH event when ridden over a vibration occluding block",
            extra = "[MC-208597](https://bugs.mojang.com/browse/MC-208597)",
            category = BUGFIX
    )
    public static boolean boatMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes spawn eggs having an offset game event when spawning an entity",
            extra = "[MC-247643](https://bugs.mojang.com/browse/MC-247643)",
            category = BUGFIX
    )
    public static boolean spawnEggOffsetEventFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Spawning entities using spawn eggs on vibration occluding blocks not occluding",
            extra = "[MC-247645](https://bugs.mojang.com/browse/MC-247645)",
            category = BUGFIX
    )
    public static boolean spawnEggMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Spawning entities using spawn eggs on entities not creating ENTITY_PLACE game event",
            extra = "[MC-214472](https://bugs.mojang.com/browse/MC-214472)",
            category = BUGFIX
    )
    public static boolean spawnEggMissingEventFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes minecarts sending the ENTITY_PLACE event when placed on a vibration occluding block",
            extra = "[MC-213823](https://bugs.mojang.com/browse/MC-213823)",
            category = BUGFIX
    )
    public static boolean minecartMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes villagers not giving a discount if you log out while they are being cured",
            extra = "[MC-247647](https://bugs.mojang.com/browse/MC-247647)",
            category = BUGFIX
    )
    public static boolean villagerDiscountIgnoresOfflinePlayersFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes foxes pathfinding to origin (0,0) during a thunderstorm",
            extra = "[MC-179916](https://bugs.mojang.com/browse/MC-179916)",
            category = BUGFIX
    )
    public static boolean foxesGoToOriginDuringThunderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes villagers converting to witches while sleeping, not changing the bed occupancy",
            extra = "[MC-167242](https://bugs.mojang.com/browse/MC-167242)",
            category = BUGFIX
    )
    public static boolean villagerToWitchBedOccupiedFix = false;

    //by FX - PR0CESS, ported from carpetmod112
    @Rule(
            desc = "Fixes reload update order for tile entities",
            extra = "Fixes instant wires randomly breaking - Effective after chunk reload.",
            category = BUGFIX
    )
    public static boolean reloadUpdateOrderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes armor stands not taking damage in lava",
            extra = "[MC-199210](https://bugs.mojang.com/browse/MC-199210)",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean armorStandNegateLavaDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes tripwires not being disarmed correctly",
            extra = "[MC-129055](https://bugs.mojang.com/browse/MC-129055)",
            category = BUGFIX
    )
    public static boolean tripwireNotDisarmingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes powder snow letting light pass through it",
            extra = "[MC-205044](https://bugs.mojang.com/browse/MC-205044)",
            category = {BUGFIX,INTENDED}
    )
    public static boolean powderedSnowOpacityFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes hoppers being invisible when placed next to a powered block",
            extra = "It fixes clients not being able to see the hopper, by redrawing them if powered",
            category = {BUGFIX,RECOMMENDED}
    )
    public static boolean invisibleHopperFix = false;


    /*

    OPTIMIZATIONS
    These are all the rules that where made to optimize the game. Rules that are considered optimizations
    but that where not intended to be optimizations are not put in this category.

     */

    //by FX - PR0CESS
    //Soon this will fix all update suppression with rails
    @Rule(
            desc = "Makes rails faster by removing most updates on themselves, duplicate updates, and doing rail search internally",
            extra = {"This kind of prevents update suppression using a normal suppressor. It requires an angled suppressor to work!",
                    "This probably changes some rail behavior although so far does not seem to do so. Does change amount of block updates tho"},
            category = OPTIMIZATION
    )
    public static boolean optimizedPoweredRails = false;

    //by 2No2Name, JellySquid
    @Rule(
            desc = "Initial made for Lithium, although was not exactly vanilla behavior. This is a very small optimization!",
            category = OPTIMIZATION
    )
    public static boolean optimizedTicketManager = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Changes Math.round to a faster implementation. Although it does not give the exact same results",
            extra = "This does not affect many things and will most likely be unnoticeable. It is ~1.28x faster",
            category = OPTIMIZATION
    )
    public static boolean optimizedRounding = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Changes MathHelper.hypot to a faster implementation. It gives nearly perfectly accurate results",
            extra = {"Currently only effect chunks blending so will likely be unnoticeable. It is ~1.6x faster",
                    "recommended that you set this on permanently, and restart the server for best results"},
            category = OPTIMIZATION
    )
    public static boolean optimizedHypot = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Changes many of the main Random() calls to use XoroShiro128++ instead",
            extra = {"This will break anything related to random, technically still possible to crack*",
                    "recommended that you set this on permanently, and restart the server for best results"},
            category = OPTIMIZATION
    )
    public static boolean optimizedRandom = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Optimized the getBiome call to be 25% - 75% faster",
            extra = "This is a fully vanilla optimization. This can optimize the client also, go check Blanket-client-tweaks for that",
            category = {OPTIMIZATION,VANILLA,RECOMMENDED}
    )
    public static boolean optimizedBiomeAccess = false;

    //by FX - PR0CESS
    //I may end up converting all the other functions in recipe manager to be faster. Although I don't need them right now
    @Rule(
            desc = "Optimized the RecipeManager getFirstMatch call to be up to 3x faster",
            extra = {"This is a fully vanilla optimization. Improves: [Blast]Furnace/Campfire/Smoker/Stonecutter/Crafting/Sheep Color Choosing",
                    "This was mostly made for the auto crafting table, since the performance boost is much more visible while using that mod"},
            category = {OPTIMIZATION,VANILLA,RECOMMENDED}
    )
    public static boolean optimizedRecipeManager = false;


    /*

    DUPE BUGS
    Bugs that dupe stuff. This is the cool bug category. They are just like all the
    other bugs, except they deserve a category of their own (for sorting purposes...)

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes falling blocks duping using the end portal",
            category = {BUGFIX,DUPE}
    )
    public static boolean fallingBlockDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            desc = "Fixes rails duplicating",
            category = {BUGFIX,DUPE}
    )
    public static boolean railDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            desc = "Fixes TNT & carpet dupers, and part of rail dupers",
            extra = {"Attachment block update based dupers will do nothing and redstone component update based dupers can no longer keep their duped block",
                    "Implementation by Carpet-TIS-Addition - Dupe bad dig good"},
            category = {BUGFIX,DUPE}
    )
    public static boolean pistonDupingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to dupe items using the /give command",
            extra = "[MC-120507](https://bugs.mojang.com/browse/MC-120507)",
            category = {BUGFIX,RECOMMENDED,DUPE}
    )
    public static boolean giveCommandDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes /data duping inventories when modifying entity data",
            extra = "[MC-112826](https://bugs.mojang.com/browse/MC-112826) & [MC-191011](https://bugs.mojang.com/browse/MC-191011)",
            category = {BUGFIX,RECOMMENDED,VANILLA,DUPE}
    )
    public static boolean nbtDataDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees duplicating while trying to load a beehive/beenest in unloaded chunks",
            extra = "[MC-234471](https://bugs.mojang.com/browse/MC-234471)",
            category = {BUGFIX,EXPERIMENTAL,DUPE}
    )
    public static boolean beeDupeFix = false;

    // by apple502j
    @Rule(
            desc = "Fixes a general item dupe using shulker boxes",
            category = {BUGFIX,RECOMMENDED,DUPE}
    )
    public static boolean breakSwapGeneralItemDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes a string dupe using water & tripwire hooks",
            extra = "[MC-59471](https://bugs.mojang.com/browse/MC-59471)",
            category = {BUGFIX,DUPE}
    )
    public static boolean stringDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes a tripwire hook dupe using doors & trapdoors",
            category = {BUGFIX,DUPE}
    )
    public static boolean tripwireHookDupeFix = false;


    /*

    RE-INTRODUCE
    Bugs that are no longer Unresolved that we reintroduce into the game
    or Bugs that where fixed in the snapshots that we re-introduce from older versions
    or just re-introducing rules/mechanics because we want to (usually performance)

    Damn these are some long rule names

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces target blocks being permanently powered when moved by pistons",
            extra = "Reverts [MC-173244](https://bugs.mojang.com/browse/MC-173244)",
            category = REINTRODUCE
    )
    public static boolean reIntroduceTargetBlockPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces lightning rods being permanently powered when moved by pistons",
            extra = "Reverts [MC-203718](https://bugs.mojang.com/browse/MC-203718)",
            category = REINTRODUCE
    )
    public static boolean reIntroduceLightningRodPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces general item dupe using dolphins, and some other dimension change dupes",
            category = {REINTRODUCE,DUPE}
    )
    public static boolean reIntroducePortalGeneralItemDupe = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces 1.12 flint and steel behavior. Flint and steel can be used for updating observers / buds",
            extra = "Reverts [MC-4923](https://bugs.mojang.com/browse/MC-4923) from 18w05a",
            category = REINTRODUCE
    )
    public static boolean reIntroduceFlintAndSteelBehavior = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces multiplayer donkey/llama dupe bug based on disconnecting while riding donkey/llama",
            extra = {"Reverts [MC-181241](https://bugs.mojang.com/browse/MC-181241) from 18w05a",
                    "This has not been tested in 1.18 and may not work!"},
            category = {REINTRODUCE,EXPERIMENTAL,DUPE}
    )
    public static boolean reIntroduceDonkeyRidingDupe = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces item shadowing!",
            extra = "[PR0CESS's Video](https://youtu.be/i8_FPyn20ns) & [Fallen_Breath's Video](https://youtu.be/mTeYwq7HaEA)",
            category = {REINTRODUCE,DUPE}
    )
    public static boolean reIntroduceItemShadowing = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces zero tick farms",
            extra = "Reverts [MC-113809](https://bugs.mojang.com/browse/MC-113809) from 20w12a",
            category = REINTRODUCE
    )
    public static boolean reIntroduceZeroTickFarms = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces only saving during autosaves instead of any time",
            extra = {"Reverts 'saving chunks whenever there is time spare to reduce autosave spikes' from 20w12a",
            "This makes your hard drive work overtime, so people with slow drives might suffer from this. This fixes that!"},
            category = REINTRODUCE
    )
    public static boolean reIntroduceOnlyAutoSaveSaving = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces very aggressive saving by removing the chunk save cooldown that was added in 22w05a",
            extra = {"For obvious reasons, this does not work when `reIntroduceOnlyAutoSaveSaving` is enabled"},
            category = REINTRODUCE
    )
    public static boolean reIntroduceVeryAggressiveSaving = false;


    /*

    FABRIC-CARPET & CARPET-EXTRA OVERRIDES
    I want to move these rules over to carpet-fixes

     */

    //By FX - PR0CESS from fabric-carpet
    @Rule(
            desc = "Fixes Lightning killing the items that drop from entities it kills",
            extra = "[MC-206922](https://bugs.mojang.com/browse/MC-206922)",
            category = BUGFIX
    )
    public static boolean lightningKillsDropsFix = false;

    //By DeadlyMC from carpet-extra
    @Rule(
            desc = "Re-adds 1.8 double retraction to pistons",
            category = {BUGFIX,EXPERIMENTAL},
            extra = { "Gives pistons the ability to double retract without side effects",
                    "Fixes [MC-88959](https://bugs.mojang.com/browse/MC-88959)" }
    )
    public static boolean doubleRetraction = false;

    //Original By DeadlyMC (from carpet-extra), Fixed By FX - PR0CESS
    //Fixed bug in carpet-extra's implementation
    @Rule(
            desc = "Fixes Quick pulses getting lost in repeater setups",
            extra = {"Probably brings back pre 1.8 behaviour",
                    "[MC-54711](https://bugs.mojang.com/browse/MC-54711)"},
            category = BUGFIX
    )
    public static boolean repeaterPriorityFix = false;


    /*

    ADVANCED CONFIGURATIONS
    Simple options that you can turn on and off are great, its one of the things I try to
    do. Since it makes it much easier for the end user to use the mod. These options are
    for more advanced users who want some more flexibility!

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to change the max amount of chunks that can save per autosave",
            extra = "If your server has large lag spikes due to chunk saving, you can lower this number.",
            options = {"300","200","100","50"},
            category = {ADVANCED,OPTIMIZATION}
    )
    public static int maxChunksSavedPerAutoSave = 200;

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to change the max amount of chunks that can save per tick for the 1.18 chunk saving system",
            extra = "This is for the system which just attempt to save chunks every tick. Max chunks saved per tick",
            options = {"100","50","20","10"},
            category = {ADVANCED,OPTIMIZATION}
    )
    public static int maxChunksSavedPerTick = 20;

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to change the delay between autosaves in ticks",
            extra = "Shortening this number will make your server save more often which will also spread the load on the server",
            options = {"12000","6000","3600","1200"},
            category = {ADVANCED,OPTIMIZATION}
    )
    public static int delayBetweenAutoSaves = 6000;

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to change max tick delay before a task is cancelled",
            extra = "Lower value raises the task leniency. Basically lower number means higher chance of success",
            options = {"12","6","3","1"},
            category = {ADVANCED,EXPERIMENTAL}
    )
    public static int maxTickLatency = 3;

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to change how long the server player list wait before updating",
            extra = {"delay is in nanoseconds. Default is 5 seconds (5000000000) [1m,10s,5s,1s]"},
            options = {"60000000000","10000000000","5000000000","1000000000"},
            category = ADVANCED
    )
    public static long statusUpdateDelay = 5000000000L;

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to toggle onlineMode without needing to restart the server",
            validate = onlineModeValidator.class,
            category = {ADVANCED,EXPERIMENTAL}
    )
    public static boolean toggleOnlineMode = CarpetServer.minecraft_server == null || CarpetServer.minecraft_server.isOnlineMode();

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to toggle preventing proxy connections without needing to restart the server",
            validate = preventProxyConnectionsValidator.class,
            category = {ADVANCED,EXPERIMENTAL}
    )
    public static boolean togglePreventProxyConnections = CarpetServer.minecraft_server != null && CarpetServer.minecraft_server.shouldPreventProxyConnections();

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to toggle pvpEnabled without needing to restart the server",
            validate = pvpEnabledValidator.class,
            category = ADVANCED
    )
    public static boolean togglePvpEnabled = CarpetServer.minecraft_server == null || CarpetServer.minecraft_server.isPvpEnabled();

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to toggle flightEnabled without needing to restart the server",
            validate = flightEnabledValidator.class,
            category = ADVANCED
    )
    public static boolean toggleFlightEnabled = CarpetServer.minecraft_server != null && CarpetServer.minecraft_server.isFlightEnabled();

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to toggle enforcing the whitelist without needing to restart the server",
            validate = enforceWhitelistValidator.class,
            category = ADVANCED
    )
    public static boolean toggleEnforceWhitelist = CarpetServer.minecraft_server != null && CarpetServer.minecraft_server.isEnforceWhitelist();

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to change the chunk save cooldown added in 22w05a",
            extra = {"Set to 10s by default, this is the delay between chunks aggressively saving",
                    "If your server has large lag spikes due to chunk saving, you can raise this number.",
                    "For obvious reasons, this rule is disabled when `reIntroduceVeryAggressiveSaving` is enabled"},
            options = {"120000","60000","10000","1000","0"},
            category = {ADVANCED,OPTIMIZATION}
    )
    public static long chunkSaveCooldownDelay = 10000L;


    /*

    PARITY
    Parity bugs between java & bedrock edition. This category is basically a meme,
    we probably don't want these to actually be added to java!

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Makes block update order random",
            category = PARITY
    )
    public static boolean parityRandomBlockUpdates = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Allows pistons to push and pull light blocks",
            category = PARITY
    )
    public static boolean parityMovableLightBlocks = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Comparators no longer output level 15 if the full block its reading from is powered if there is a container behind it",
            extra = "Relates to [MC-64394](https://bugs.mojang.com/browse/MC-64394) which marked as Works As Intended",
            category = PARITY
    )
    public static boolean parityTerribleComparators = false;


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

    private static class WorldBorderCollisionRoundingFixValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            CarpetFixesInit.scheduleWorldBorderReset = true;
            return newValue;
        }
    }

    private static class onlineModeValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setOnlineMode(newValue);
            return newValue;
        }
    }

    private static class preventProxyConnectionsValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setPreventProxyConnections(newValue);
            return newValue;
        }
    }

    private static class pvpEnabledValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setPvpEnabled(newValue);
            return newValue;
        }
    }

    private static class flightEnabledValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setFlightEnabled(newValue);
            return newValue;
        }
    }

    private static class enforceWhitelistValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setEnforceWhitelist(newValue);
            return newValue;
        }
    }
}

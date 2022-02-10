package carpetfixes;

import carpet.CarpetServer;
import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import carpetfixes.settings.Bug;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.*;
import static carpetfixes.settings.CategoryType.*;
import static carpetfixes.settings.ResolutionType.*;
import static carpetfixes.settings.RuleCategory.PARITY;
import static carpetfixes.settings.RuleCategory.*;

public class CarpetFixesSettings {

    //Add your name above the rules so people know who to contact about changing the code. E.x. By FX - PR0CESS

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes End Crystals not exploding when damaged from explosions. End Crystal chaining",
            category = BUGFIX
    )
    @Bug(reports="MC-118429",categories=ENTITIES)
    public static boolean crystalExplodeOnExplodedFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes sponges not giving block updates when absorbing water",
            category = BUGFIX
    )
    @Bug(reports="MC-220636")
    public static boolean spongeUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Multiple Updates
    @Rule(
            desc = "Fixes hoppers not giving block updates when placed while powered",
            extra = {"As a side-effect, it fixes clients not being able to see the hopper",
                    "(Youtube Video)[https://www.youtube.com/watch?v=QVOONJ1OY44]"},
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug()
    public static boolean hopperUpdateFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes observers not giving block updates when retracted and immediately repowered",
            category = BUGFIX
    )
    @Bug(reports="MC-136566",categories=REDSTONE)
    public static boolean observerUpdateFix = false;

    //By FX - PR0CESS
    //Recommended since it allows illegal blocks to be made. Suppresses Lots of Update
    //Still requires more testing to perfect
    /*@Rule(
            desc = "Fixes sticky piston heads not giving a block update when failing to pull slime",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-185572")
    public static boolean pistonPullingUpdateFix = false;*/

    //By FX - PR0CESS
    //Not putting it in the dupe fix category since technically there is no way to dupe with it anymore. Although there probably will be
    @Rule(
            desc = "Fixes StringTag Exploits due to StringTag writeUTF() not respecting readUTF() Limits causing crashes internally",
            extra = "This was the cause of ChunkRegen, and the book dupe. Both patched, although StringTag is still broken",
            category = {BUGFIX,CRASHFIX,RECOMMENDED,VANILLA,DUPE}
    )
    @Bug(reports="MC-134892",categories=NETWORKING)
    public static boolean stringTagExploitFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect block collision checks for players",
            extra = "This bug allows you to teleport to the end at your overworld position, amongst many other problems",
            category = BUGFIX
    )
    @Bug(reports="MC-123364",categories={COLLISION,PLAYER})
    public static boolean blockCollisionCheckFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes issues where block updates are directional. Changes block update order from XYZ to XZY",
            extra = "Warning! This changes how block updates are done and could effect some contraptions",
            category = BUGFIX
    )
    @Bug(reports="MC-161402",categories=REDSTONE)
    public static boolean blockUpdateOrderFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes the issue where comparators don't always get updated correctly",
            category = BUGFIX
    )
    @Bug(reports="MC-120986",categories=REDSTONE)
    public static boolean comparatorUpdateFix = false;

    //by FX - PR0CESS
    //Recommended since it not only negates a crash but also tried to keep behaviour after it. Technically it's a dupe fix, although it's a lot more than that
    @Rule(
            desc = "Prevents update suppression from working. When the stack is reached, the following updates are moved to the next tick",
            extra = "This does not prevent stack overflow exploits, it simple makes sure to update block after",
            category = {BUGFIX,CRASHFIX,RECOMMENDED,DUPE}
    )
    @Bug(categories=CRASH)
    public static boolean updateSuppressionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some entities not bouncing on slime blocks and getting stuck",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(reports="MC-216985",categories=ENTITIES)
    public static boolean incorrectBounceLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some entities getting stuck in bubble columns",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(reports="MC-207866",categories=ENTITIES)
    public static boolean incorrectBubbleColumnLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes movement slowdown being calculated based on last block in search. Uses the slowest value instead",
            category = BUGFIX
    )
    @Bug(reports="MC-202654",categories=ENTITIES)
    public static boolean directionalBlockSlowdownFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes cats sometimes breaking there leads after giving a gift",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-202607",categories=MOB_BEHAVIOUR)
    public static boolean catsBreakLeadsDuringGiftFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Leashed pets teleporting to the player when reloaded, breaking there leads",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-173303",categories=ENTITIES)
    public static boolean petsBreakLeadsDuringReloadFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes enderman not updating the block they place correctly",
            extra = "Only applies if you use datapacks or mods. E.x. Enderman placing a wither skull will not spawn a wither",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(reports="MC-183054",categories={COMMANDS,MOB_BEHAVIOUR})
    public static boolean endermanDontUpdateOnPlaceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes enderman constantly trying to teleport when in a minecart under daylight",
            category = {BUGFIX,VANILLA}
    )
    @Bug(reports="MC-227008")
    public static boolean endermanUselessMinecartTeleportingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails updating other rails before checking if they are in a valid location",
            category = BUGFIX
    )
    @Bug(reports="MC-174864",categories={BLOCK_STATES,REDSTONE})
    public static boolean railInvalidUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails on being moved, allowing for invalid states",
            extra = "Prevents redstone budding from working",
            category = BUGFIX
    )
    @Bug(reports="MC-123311",categories=REDSTONE)
    public static boolean railMissingUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails after being moved",
            category = BUGFIX
    )
    @Bug(reports="MC-96224",categories=REDSTONE)
    public static boolean railMissingUpdateAfterPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the void rings in the end, which is a floating point error",
            category = {BUGFIX,OPTIMIZATION}
    )
    @Bug(reports="MC-159283",categories=WORLD_GENERATION)
    public static boolean endVoidRingsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fall damage being delayed by sleeping, fall damage will be removed instead",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-19830",categories=PLAYER)
    public static boolean sleepingDelaysFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes TNT Entity not being able to go through nether portals",
            category = BUGFIX
    )
    @Bug(reports="MC-8983",categories=ENTITIES)
    public static boolean tntCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Falling Blocks not being able to go through nether portals",
            category = BUGFIX
    )
    @Bug(reports="MC-9644",categories=ENTITIES)
    public static boolean fallingBlocksCantUseNetherPortalsFix = false;

    //by FX - PR0CESS
    //Marked as vanilla since it does not change any behaviour, just keeps spawn chunks loaded
    @Rule(
            desc = "Fixes Spawn Chunks not ticking entities and block entities if no player online",
            category = {BUGFIX,VANILLA}
    )
    @Bug(reports="MC-59134",categories=ENTITIES)
    public static boolean spawnChunkEntitiesUnloadingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Named Blocks not stacking due to useless RepairCost tag",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-197473")
    public static boolean repairCostItemNotStackingFix = false;

    //By Skyrising
    @Rule(
            desc = "Fixes enchantments not working on tridents if thrown by a drowned",
            category = BUGFIX
    )
    @Bug(reports="MC-127321",categories={ITEMS,MOB_BEHAVIOUR})
    public static boolean drownedEnchantedTridentsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes multiple bugs related to effects happening only when player center in block instead of hitbox",
            category = BUGFIX
    )
    @Bug(reports={"MC-1133","MC-158154"},categories={PLAYER,COLLISION,HITBOXES})
    public static boolean playerBlockCollisionUsingCenterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect cat types spawning inside swamp huts",
            extra = "[MC-147659](https://bugs.mojang.com/browse/MC-147659)",
            category = {BUGFIX,RECOMMENDED,VANILLA}
    )
    @Bug(reports="MC-147659",categories={MOB_SPAWNING,STRUCTURES},resolution=WONT_FIX)
    public static boolean witchHutsSpawnIncorrectCatFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect sea level height being used when datapacks change the sea height",
            category = BUGFIX
    )
    @Bug(reports="MC-226687",categories={CUSTOM_WORLDS,WORLD_GENERATION})
    public static boolean hardcodedSeaLevelFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to fish outside of water",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-175544",categories=ITEMS)
    public static boolean fishingOutsideWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes experience orbs treating flowing lava as a full block",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-226961")
    public static boolean xpOrbCollisionFix = false;

    //by FX - PR0CESS
    //Currently only Slime, Mushroom, Zombie, Zombie Villager, & piglins are supported. More to come eventually when I stop being lazy
    @Rule(
            desc = "Fixes multiple different mob conversions, not transferring all the correct nbt",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(reports="MC-88967",categories=ENTITIES)
    public static boolean conversionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Explosions being able to destroy item frames in water",
            category = BUGFIX
    )
    @Bug(reports="MC-3697",categories=ENTITIES)
    public static boolean explosionBreaksItemFrameInWaterFix = false; //todo: wrong fix xD

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Moving Blocks from destroying path blocks",
            category = BUGFIX
    )
    @Bug(reports="MC-161026",categories=REDSTONE)
    public static boolean movingBlocksDestroyPathFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Withers and Golems not spawning due to replaceable blocks being in the way",
            validate = WitherGolemSpawningFixValidator.class,
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-60792",categories=MOB_SPAWNING)
    public static boolean witherGolemSpawningFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Breaking blocks that should not be able to be broken using headless pistons",
            extra = "Illegal blocks are any blocks that have a hardness value of -1.0F",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-188220",related="MC-27056")
    public static boolean illegalBreakingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make and use Headless Pistons",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-27056",categories=REDSTONE)
    public static boolean headlessPistonFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the thunder timer being reset whenever players sleep",
            category = BUGFIX
    )
    @Bug(related={"MC-63340","MC-222596"},categories=PLAYER)
    public static boolean sleepingResetsThunderFix = false;

    //by FX - PR0CESS
    /*@Rule(
            desc = "Fixes changing between spectator lowering your player",
            category = BUGFIX
    )
    @Bug(reports="MC-146582",categories=PLAYER)
    public static boolean spectatorLowersPlayerFix = false;*/

    //code by FX - PR0CESS
    //solution by DawNemo
    @Rule(
            desc = "Fixes incorrect explosion exposure calculations",
            category = {BUGFIX,RECOMMENDED,VANILLA}
    )
    @Bug(reports="MC-232355",categories=ENTITIES)
    public static boolean incorrectExplosionExposureFix = false;

    //by FX - PR0CESS
    //Marked as Vanilla since its very, very hard to run into a situation where it affects vanilla
    //Only technical players would be able to tell the difference if they really tried
    @Rule(
            desc = "Fixes some redstone components sending duplicated block updates",
            category = {BUGFIX,RECOMMENDED,VANILLA,OPTIMIZATION}
    )
    @Bug(reports="MC-231071",categories={REDSTONE,PERFORMANCE})
    public static boolean duplicateBlockUpdatesFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some blocks not popping off when a trapdoor opens",
            category = BUGFIX
    )
    @Bug(reports="MC-157300",categories=BLOCK_STATES)
    public static boolean trapdoorMissingUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the detector rail giving useless comparator updates on entity collision",
            category = BUGFIX
    )
    @Bug() //No bug report has been made about this bug yet
    public static boolean uselessDetectorRailUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees getting stuck in the void due to gravity being disabled",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-167279",categories=MOB_BEHAVIOUR)
    public static boolean beeStuckInVoidFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make permanent invulnerable end crystals",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(categories=ENTITIES)
    public static boolean invulnerableEndCrystalFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes signal strength being inaccurate and skipping odd signal strengths due to precision loss with distance",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(reports="MC-218222",categories={GAME_EVENTS,REDSTONE})
    public static boolean sculkSensorPrecisionLossFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes creepers resetting there fuse duration when travelling through a nether portal after being ignited by flint & steel",
            category = BUGFIX
    )
    @Bug(reports="MC-234754")
    public static boolean creeperPortalFuseResetsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes soul speed enchantment from damaging boots when riding a vehicle",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-200991")
    public static boolean soulSpeedIncorrectDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes placing end crystals too early not re-summoning the Ender Dragon",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-215763")
    public static boolean endCrystalPlacingTooEarlyFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to respawn the ender dragon using only 2 ender crystals instead of the intended 4",
            category = BUGFIX
    )
    @Bug(reports="MC-102774",categories=MOB_SPAWNING)
    public static boolean respawnDragonWithoutAllEndCrystalsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes trees considering the bottom block as a trunk and modifying leaves because of it",
            category = BUGFIX
    )
    @Bug()
    public static boolean treeTrunkLogicFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes blocks using updateNeighbors() on blocks next to them, making itself get a block update even though it does not accept block updates",
            extra = "Some blocks do accept block updates, although we make sure to update them correctly",
            category = {BUGFIX,RECOMMENDED,VANILLA,OPTIMIZATION}
    )
    @Bug(related="MC-231071",categories={REDSTONE,PERFORMANCE})
    public static boolean uselessSelfBlockUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Tnt Minecarts being able to explode twice, killing their own drops",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(categories=ENTITIES)
    public static boolean tntMinecartExplodesTwiceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Zombies & Vindicators being able to break any block where a door was",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-95467",categories=MOB_BEHAVIOUR)
    public static boolean breakAnythingDoorGoalFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Blackstone Buttons taking longer then other buttons to break",
            category = BUGFIX
    )
    @Bug(reports="MC-199752",categories=ITEMS)
    public static boolean blackstoneButtonBreakSpeedFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Transparent blocks placed between bookshelves and enchanting tables negating bonuses received",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-2474",categories=BLOCK_STATES)
    public static boolean transparentBlocksNegateEnchantingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes chests being accessible outside the world border by placing a chest near it",
            extra = "We fix this by preventing chests from merging with other chests outside of the world border",
            category = BUGFIX
    )
    @Bug(reports="MC-67844")
    public static boolean chestUsablePastWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes item frames playing a sound when they are read from nbt",
            category = {BUGFIX,VANILLA}
    )
    @Bug(reports="MC-123450",categories={COMMANDS,SOUND})
    public static boolean itemFramePlaysSoundOnReadFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes commands not allowing all nbt tags to work correctly",
            category = {BUGFIX,VANILLA,RECOMMENDED,CREATIVE}
    )
    @Bug(reports="MC-112257",categories={COMMANDS,DATA_PACKS})
    public static boolean incorrectNbtChecks = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the end portal removing your status effects when going from the overworld to the end",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-6431",categories=PLAYER)
    public static boolean endPortalRemovesEffectsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes burnt-out redstone torches having inconsistent behavior for turning on again",
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports="MC-120938",categories=REDSTONE)
    public static boolean inconsistentRedstoneTorchFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes gamerule doMobLoot not effecting foxes from dropping their items",
            category = BUGFIX
    )
    @Bug(reports="MC-153010",categories=ENTITIES)
    public static boolean foxesDropItemsWithLootOffFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes saved worlds corrupting due to missing structures",
            extra = "Make sure to always make a backup before using this, since its not been tested in the recent versions",
            category = {BUGFIX,CRASHFIX,EXPERIMENTAL,VANILLA}
    )
    @Bug(reports="MC-194811",categories={CUSTOM_WORLDS,SAVE_DATA,STRUCTURES})
    public static boolean missingStructureCorruptionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fluids being able to instantly flow!",
            category = BUGFIX
    )
    @Bug(reports="MC-215636")
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
            category = {BUGFIX,RECOMMENDED}
    )
    @Bug(reports={"MC-207289","MC-207635"},categories=GAME_EVENTS)
    public static boolean sculkSensorBiasFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees not leaving the hive in the end & nether due to weather conditions",
            category = BUGFIX
    )
    @Bug(reports="MC-168329",categories=MOB_BEHAVIOUR)
    public static boolean beeNotLeavingHiveFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes item frames & paintings being able to interact with pressure plates & tripwire hooks, causing them to not de-power",
            category = BUGFIX
    )
    @Bug(reports="MC-82055",categories=REDSTONE)
    public static boolean hangingEntityTriggersTrapsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes boats breaking and giving fall damage under certain circumstances",
            category = BUGFIX
    )
    @Bug(reports="MC-119369",related="MC-98160",categories={COLLISION,ENTITIES})
    public static boolean boatsTakeFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes boats not being destroyed by fall damage",
            category = BUGFIX
    )
    @Bug(reports="MC-98160",related="MC-119369",categories={COLLISION,ENTITIES})
    public static boolean boatsDontTakeFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes buried treasure always generating in the center of a chunk",
            extra = "[MC-227443](https://bugs.mojang.com/browse/MC-227443)",
            category = BUGFIX
    )
    @Bug(reports="MC-227443",categories=WORLD_GENERATION)
    public static boolean buriedTreasureAlwaysCenterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes reinforcements only spawning zombies",
            category = BUGFIX
    )
    @Bug(reports="MC-14800",categories=MOB_SPAWNING)
    public static boolean reinforcementsOnlySpawnZombiesFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect fall distance calculations causing fall distance to build up over time with leads",
            category = BUGFIX
    )
    @Bug(reports="MC-14167",categories=MOB_BEHAVIOUR)
    public static boolean incorrectFallDamageFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes the void killing loyalty tridents, even though they should come back to the player",
            category = BUGFIX
    )
    @Bug(reports="MC-125755",resolution=WAI)
    public static boolean voidKillsLoyaltyTridentsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes piercing projectiles lowering there piercing when 'hitting' an enderman",
            category = BUGFIX
    )
    @Bug(reports="MC-145557",categories={ENTITIES,MOB_BEHAVIOUR})
    public static boolean endermanLowerPiercingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes placing a button/pressure plate inside of a projectile not activating it",
            category = BUGFIX
    )
    @Bug(reports="MC-209284")
    public static boolean projectileNotDetectedOnPlaceFix = false;

    //by FX - PR0CESS
    //Might make it so no arrows bypass the totem, unsure yet
    @Rule(
            desc = "Fixes arrows of harming bypassing Totems of Undying",
            category = BUGFIX
    )
    @Bug(reports="MC-206307",categories={ENTITIES,PLAYER})
    public static boolean arrowEffectsBypassTotemsFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes mobs continuing to convert even if the block is no longer there",
            category = BUGFIX
    )
    @Bug(reports="MC-227250",categories=MOB_BEHAVIOUR)
    public static boolean mobsConvertingWithoutBlocksFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Mobs ignoring 'Owner' when picking up loot",
            category = BUGFIX
    )
    @Bug(reports="MC-120578",categories={COMMANDS,MOB_BEHAVIOUR})
    public static boolean mobsIgnoreOwnerOnPickupFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes redstone torch update order when being broken, causing unnatural updates",
            category = BUGFIX
    )
    @Bug(reports="MC-157644",related="MC-196649",categories=REDSTONE)
    public static boolean redstoneTorchOrderOnBreakFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes falling blocks not behaving as expected when being teleported",
            category = BUGFIX
    )
    @Bug(reports="MC-151488",categories=COMMANDS)
    public static boolean fallingBlockTeleportingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes player velocity on X and Z axis being cancelled separately at low values",
            category = BUGFIX
    )
    @Bug(reports="MC-241951",categories=PLAYER)
    public static boolean velocitySeparateAxisCancellingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes ArmorStands losing functionality due to 'optimizations'",
            extra = "The marker tag will still prevent interaction though",
            category = BUGFIX
    )
    @Bug(reports="MC-244956")
    public static boolean armorStandMissingFunctionalityFix = false;

    //by Adryd
    @Rule(
            desc = "Fixes enderman teleporting when they have the NoAI tag",
            category = BUGFIX
    )
    @Bug(reports="MC-65668",resolution=WONT_FIX)
    public static boolean endermanTeleportWithoutAIFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes redstone redirection missing updates on redirecting",
            category = BUGFIX
    )
    @Bug(reports="MC-3703",categories=REDSTONE)
    public static boolean redstoneRedirectionMissingUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to push waterlogged blocks while retaining the waterlogged state using a short pulse",
            category = BUGFIX
    )
    @Bug(reports="MC-130183",categories=BLOCK_STATES)
    public static boolean pistonsPushWaterloggedBlocksFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes tile drops at the world border spawning at the wrong location",
            category = BUGFIX
    )
    @Bug(related="MC-4",categories={ITEMS,ENTITIES})
    public static boolean tileDropsAffectedByFloatingPointFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes players being able to crack there player seed",
            extra = {"This makes it so random is shared between all entities. Which is a good performance boost",
                    "recommended that you set this on permanently, and restart the server for best results"},
            category = {BUGFIX,OPTIMIZATION}
    )
    @Bug(categories=PERFORMANCE)
    public static boolean entityRandomCrackingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes world border collision rounding to blocks for entity collisions",
            validate = WorldBorderCollisionRoundingFixValidator.class,
            category = BUGFIX
    )
    @Bug(reports={"MC-88482","MC-247422"},resolution=WAI)
    public static boolean worldBorderCollisionRoundingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes comparator signal ending prematurely due to offset block updates",
            category = BUGFIX
    )
    @Bug(reports="MC-247420",categories=REDSTONE)
    public static boolean detectorRailOffsetUpdateFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to place blocks outside of the world border",
            category = BUGFIX
    )
    @Bug(reports={"MC-63578","MC-223613"},categories=BLOCK_STATES)
    public static boolean placeBlocksOutsideWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes pistons being able to push blocks outside of the world border",
            category = BUGFIX
    )
    @Bug(reports="MC-82010",categories={BLOCK_STATES,RENDERING})
    public static boolean incorrectPistonWorldBorderCheckFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes explosions being able to destroy blocks outside of the world border",
            category = BUGFIX
    )
    @Bug(reports="MC-54606",categories=BLOCK_STATES)
    public static boolean explosionsBypassWorldBorderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes players sending the STEP event before the HIT_GROUND event",
            category = BUGFIX
    )
    @Bug(reports="MC-247417",categories=GAME_EVENTS)
    public static boolean playerStepEventFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes projectiles sending the PROJECTILE_LAND event when landing on a vibration occluding block",
            category = BUGFIX
    )
    @Bug(reports="MC-208771",categories=GAME_EVENTS)
    public static boolean projectileMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes boats sending the SPLASH event when ridden over a vibration occluding block",
            category = BUGFIX
    )
    @Bug(reports="MC-208597",categories=GAME_EVENTS)
    public static boolean boatMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes spawn eggs having an offset game event when spawning an entity",
            category = BUGFIX
    )
    @Bug(reports="MC-247643",categories=GAME_EVENTS)
    public static boolean spawnEggOffsetEventFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Spawning entities using spawn eggs on vibration occluding blocks not occluding",
            category = BUGFIX
    )
    @Bug(reports="MC-247645",categories=GAME_EVENTS)
    public static boolean spawnEggMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Spawning entities using spawn eggs on entities not creating ENTITY_PLACE game event",
            category = BUGFIX
    )
    @Bug(reports="MC-214472",categories=GAME_EVENTS)
    public static boolean spawnEggMissingEventFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes minecarts sending the ENTITY_PLACE event when placed on a vibration occluding block",
            category = BUGFIX
    )
    @Bug(reports="MC-213823",categories=GAME_EVENTS)
    public static boolean minecartMissingOcclusionFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes villagers not giving a discount if you log out while they are being cured",
            category = BUGFIX
    )
    @Bug(reports="MC-247647",categories=VILLAGE_SYSTEM)
    public static boolean villagerDiscountIgnoresOfflinePlayersFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes foxes pathfinding to origin (0,0) during a thunderstorm",
            category = BUGFIX
    )
    @Bug(reports="MC-179916",categories={MOB_BEHAVIOUR,PERFORMANCE})
    public static boolean foxesGoToOriginDuringThunderFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes villagers converting to witches while sleeping, not changing the bed occupancy",
            category = BUGFIX
    )
    @Bug(reports="MC-167242",categories=BLOCK_STATES)
    public static boolean villagerToWitchBedOccupiedFix = false;

    //by FX - PR0CESS, ported from carpetmod112
    @Rule(
            desc = "Fixes reload update order for tile entities",
            extra = "Fixes instant wires randomly breaking. Effective after chunk reload",
            category = BUGFIX
    )
    @Bug(reports="MC-8457",categories=REDSTONE)
    public static boolean reloadUpdateOrderFix = false;


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
    @Bug()
    public static boolean fallingBlockDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            desc = "Fixes rails duplicating",
            category = {BUGFIX,DUPE}
    )
    @Bug()
    public static boolean railDuplicationFix = false;

    //by Fallen-Breath from Carpet-TIS-Addition
    @Rule(
            desc = "Fixes TNT & carpet dupers, and part of rail dupers",
            extra = {"Attachment block update based dupers will do nothing and redstone component update based dupers can no longer keep their duped block",
                    "Implementation by Carpet-TIS-Addition - Dupe bad dig good"},
            category = {BUGFIX,DUPE}
    )
    @Bug()
    public static boolean pistonDupingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to dupe items using the /give command",
            category = {BUGFIX,RECOMMENDED,DUPE}
    )
    @Bug(reports="MC-120507",categories={COMMANDS,DATA_PACKS})
    public static boolean giveCommandDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes /data duping inventories when modifying entity data",
            category = {BUGFIX,RECOMMENDED,VANILLA,DUPE}
    )
    @Bug(reports={"MC-112826","MC-191011"},categories={COMMANDS,DATA_PACKS},resolution=INVALID)
    public static boolean nbtDataDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes bees duplicating while trying to load a beehive/beenest in unloaded chunks",
            category = {BUGFIX,EXPERIMENTAL,DUPE}
    )
    @Bug(reports="MC-234471",resolution=INVALID)
    public static boolean beeDupeFix = false;

    // by apple502j
    @Rule(
            desc = "Fixes a general item dupe using shulker boxes",
            category = {BUGFIX,RECOMMENDED,DUPE}
    )
    @Bug()
    public static boolean breakSwapGeneralItemDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes a string dupe using water & tripwire hooks",
            category = {BUGFIX,DUPE}
    )
    @Bug()
    public static boolean stringDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes a tripwire hook dupe using doors & trapdoors",
            category = {BUGFIX,DUPE}
    )
    @Bug()
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
            category = REINTRODUCE
    )
    @Bug(reports="MC-173244",categories=REDSTONE,reIntroduce=true)
    public static boolean reIntroduceTargetBlockPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces lightning rods being permanently powered when moved by pistons",
            category = REINTRODUCE
    )
    @Bug(reports="MC-203718",categories={BLOCK_STATES,REDSTONE},reIntroduce=true)
    public static boolean reIntroduceLightningRodPermanentlyPowered = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces general item dupe using dolphins, and some other dimension change dupes",
            category = {REINTRODUCE,DUPE}
    )
    @Bug(reIntroduce=true)
    public static boolean reIntroducePortalGeneralItemDupe = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces 1.12 flint and steel behavior. Flint and steel can be used for updating observers / buds. From 18w05a",
            category = REINTRODUCE
    )
    @Bug(reports="MC-4923",reIntroduce=true)
    public static boolean reIntroduceFlintAndSteelBehavior = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces multiplayer donkey/llama dupe bug based on disconnecting while riding donkey/llama. From 18w05a",
            category = {REINTRODUCE,EXPERIMENTAL,DUPE}
    )
    @Bug(reports="MC-181241",reIntroduce=true)
    public static boolean reIntroduceDonkeyRidingDupe = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces item shadowing!",
            extra = "[PR0CESS's Video](https://youtu.be/i8_FPyn20ns) & [Fallen_Breath's Video](https://youtu.be/mTeYwq7HaEA)",
            category = {REINTRODUCE,DUPE}
    )
    @Bug(reIntroduce=true)
    public static boolean reIntroduceItemShadowing = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces zero tick farms. From 20w12a",
            category = REINTRODUCE
    )
    @Bug(reports="MC-113809",categories=REDSTONE,reIntroduce=true)
    public static boolean reIntroduceZeroTickFarms = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces only saving during autosaves instead of any time",
            extra = {"Reverts 'saving chunks whenever there is time spare to reduce autosave spikes' from 20w12a",
            "This makes your hard drive work overtime, so people with slow drives might suffer from this. This fixes that!"},
            category = REINTRODUCE
    )
    @Bug(reIntroduce=true)
    public static boolean reIntroduceOnlyAutoSaveSaving = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Re-introduces very aggressive saving by removing the chunk save cooldown that was added in 22w05a",
            extra = {"For obvious reasons, this does not work when `reIntroduceOnlyAutoSaveSaving` is enabled"},
            category = REINTRODUCE
    )
    @Bug(reIntroduce=true)
    public static boolean reIntroduceVeryAggressiveSaving = false;


    /*

    FABRIC-CARPET & CARPET-EXTRA OVERRIDES
    I want to move these rules over to carpet-fixes

     */

    //By FX - PR0CESS from fabric-carpet
    @Rule(
            desc = "Fixes Lightning killing the items that drop from entities it kills",
            category = BUGFIX
    )
    @Bug(reports="MC-206922",categories={ITEMS,ENTITIES})
    public static boolean lightningKillsDropsFix = false;

    //By DeadlyMC from carpet-extra
    @Rule(
            desc = "Re-adds 1.8 double retraction to pistons",
            extra = "Gives pistons the ability to double retract without side effects",
            category = {BUGFIX,EXPERIMENTAL}
    )
    @Bug(reports="MC-88959",categories=REDSTONE)
    public static boolean doubleRetraction = false;

    //Original By DeadlyMC (from carpet-extra), Fixed By FX - PR0CESS
    //Fixed bug in carpet-extra's implementation
    @Rule(
            desc = "Fixes Quick pulses getting lost in repeater setups",
            extra = "Probably brings back pre 1.8 behaviour",
            category = BUGFIX
    )
    @Bug(reports="MC-54711",categories=REDSTONE)
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
    public static boolean togglePreventProxyConnections = CarpetServer.minecraft_server == null || CarpetServer.minecraft_server.shouldPreventProxyConnections();

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
    public static boolean toggleFlightEnabled = CarpetServer.minecraft_server == null || CarpetServer.minecraft_server.isFlightEnabled();

    //By FX - PR0CESS
    @Rule(
            desc = "Allows you to toggle enforcing the whitelist without needing to restart the server",
            validate = enforceWhitelistValidator.class,
            category = ADVANCED
    )
    public static boolean toggleEnforceWhitelist = CarpetServer.minecraft_server == null || CarpetServer.minecraft_server.isEnforceWhitelist();

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

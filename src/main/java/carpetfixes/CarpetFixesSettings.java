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

    //Don't include BUGFIX if the bug is not marked as Unresolved

    public enum PresetSettings {
        VANILLA, //Sets all rules to there default value
        BACKPORT, //Sets all backports to true
        CRASHFIX, //Sets all crashFixes to true
        STABILITY, //Sets all crashfixes and stability fixes (Makes the game more stable)
        NOTBACKPORTS, //Sets all rules to true except for backports (That are not crashfixes or stability fixes)
        ALL, //Sets all rules to be the opposite of there default value
        CUSTOM //Default (Does not get checked)
    }

    //Add your name above the rules so people know who to contact about changing the code. E.x. By FX - PR0CESS

    //By FX - PR0CESS
    //Disabled until I figure out why it causes a crash, issue #15
    /*@Rule(
            desc = "This rule allows you to change all Carpet-Fixes rules at the same time!",
            extra = {
                    "Vanilla: All rules set to there default value",
                    "CrashFix: Only rules that fix Crashes are enabled",
                    "Stability: Rules that make the game stable",
                    "All: Enable all rules",
                    "Custom: Default (Does not change commands)"
            },
            category = {CARPETFIXES}
    )
    public static PresetSettings carpetFixesPreset = PresetSettings.VANILLA;*/

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
    @Rule(
            desc = "Makes it so that hoppers give block updates when placed while powered",
            extra = "Fixes https://www.youtube.com/watch?v=QVOONJ1OY44",
            category = {CARPETFIXES,BUGFIX}
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
    /*
    To Be Implemented
    @Rule(
            desc = "Fixes sticky piston heads not giving a block update when failing to pull slime",
            extra = "Fixes [MC-185572](https://bugs.mojang.com/browse/MC-185572)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean pistonPullingUpdateFix = false;
     */

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes StringTag Exploits due to StringTag writeUTF() not respecting readUTF() Limits causing crashes internally",
            extra = "Fixes ChunkRegen & [MC-134892](https://bugs.mojang.com/browse/MC-134892)",
            category = {CARPETFIXES,BUGFIX,CRASHFIX}
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
    @Rule(
            desc = "Prevents update suppression from working. When the stack is reached, the following updates are moved to the next tick",
            category = {CARPETFIXES,BUGFIX,CRASHFIX}
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
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean catsBreakLeadsDuringGiftFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Leashed pets teleporting to the player when reloaded",
            extra = "Fixes [MC-173303](https://bugs.mojang.com/browse/MC-173303)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
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
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean endermanUselessMinecartTeleportingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails updating other rails before checking if they are in a valid location",
            extra = "Fixes [MC-174864](https://bugs.mojang.com/browse/MC-174864)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean railInvalidUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails on being moved, allowing for invalid states",
            extra = {"Prevents redstone budding from working","Fixes [MC-123311](https://bugs.mojang.com/browse/MC-123311)"},
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean railMissingUpdateOnPushFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails not updating other rails after being moved",
            extra = "Fixes [MC-96224](https://bugs.mojang.com/browse/MC-96224)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
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
    @Rule(
            desc = "Fixes getting kicked for flying too long when jumping and riding an entity",
            extra = "Fixes [MC-98727](https://bugs.mojang.com/browse/MC-98727)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean mountingFlyingTooLongFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes fall damage being delayed by sleeping, fall damage will be removed instead",
            extra = "Fixes [MC-19830](https://bugs.mojang.com/browse/MC-19830)",
            category = {CARPETFIXES,BUGFIX}
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
    @Rule(
            desc = "Fixes Spawn Chunks not ticking entities and block entities if no player online",
            extra = "Fixes [MC-59134](https://bugs.mojang.com/browse/MC-59134)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean spawnChunkEntitiesUnloadingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Named Blocks not stacking due to useless RepairCost tag",
            extra = "Fixes [MC-197473](https://bugs.mojang.com/browse/MC-197473)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
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
            category = {CARPETFIXES,BUGFIX}
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
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean fishingOutsideWaterFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to dupe items using the /give command",
            extra = "Fixes [MC-120507](https://bugs.mojang.com/browse/MC-120507)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean giveCommandDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes experience orbs acting as if flowing lava is a full block",
            extra = "Fixes [MC-226961](https://bugs.mojang.com/browse/MC-226961)",
            category = {CARPETFIXES,BUGFIX}
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
            category = {CARPETFIXES,BUGFIX,INTENDED}
    )
    public static boolean witherGolemSpawningFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes Bedrock Breaking using headless pistons",
            extra = "Fixes [MC-188220](https://bugs.mojang.com/browse/MC-188220)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean bedrockBreakingFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes being able to make and use Headless Pistons",
            extra = "Fixes [MC-27056](https://bugs.mojang.com/browse/MC-27056)",
            category = {CARPETFIXES,BUGFIX}
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
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean incorrectExplosionExposureFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes /data duping inventories when modifying entity data",
            extra = "Fixes [MC-112826](https://bugs.mojang.com/browse/MC-112826) & [MC-191011](https://bugs.mojang.com/browse/MC-191011)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean nbtDataDupeFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some redstone components send duplicated block updates",
            extra = "Fixes [MC-231071](https://bugs.mojang.com/browse/MC-231071)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean duplicateBlockUpdatesFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes some blocks not popping off when a trapdoor opens",
            extra = "Fixes [MC-157300](https://bugs.mojang.com/browse/MC-157300)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean trapdoorMissingUpdateFix = false;

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

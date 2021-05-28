package carpetfixes;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class CarpetFixesSettings {

    private final static String CARPETFIXES = "carpet-fixes";
    private final static String CRASHFIX = "crashfix"; // For bugs that fix possible server crashes
    private final static String BACKPORT = "backport"; //A bug that got fixed which we don't want fixed or came from a snapshot
    private final static String WONTFIX = "wontfix"; //Marked as `won't fix` on the bug tracker

    //Marked as `Works as Intended` on the bug tracker. I don't like these, usually will only implement for backports
    // It's not Vanilla if you aren't playing the game as the developers intended it to be played :thonk:
    private final static String INTENDED = "intended";

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
    @Rule(
            desc = "This rule allows you to change all Carpet-Fixes rules at the same time!",
            extra = {"Vanilla: All rules set to there default value",
                    "CrashFix: Only rules that fix Crashes are enabled",
                    "Stability: Rules that make the game stable",
                    "All: Enable all rules",
                    "Custom: Default (Does not change commands)"
            },
            category = {CARPETFIXES}
    )
    public static PresetSettings carpetFixesPreset = PresetSettings.CUSTOM;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes World Modifying tasks to be before decorations [lakes]",
            extra = {"Warning! This is extremely unstable and modifies vanilla mechanics. Should be enabled before stating the server!","Fixes [MC-610](https://bugs.mojang.com/browse/MC-610)"},
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean worldgenIncorrectOrderFix = false;

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
            desc = "Fixes Chunk Regen due to StringTag writeUTF() not respecting readUTF() Limits",
            extra = "Fixes ChunkRegen & [MC-134892](https://bugs.mojang.com/browse/MC-134892)",
            category = {CARPETFIXES,BUGFIX,CRASHFIX}
    )
    public static boolean chunkRegenFix = false;

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
    /* Not working as Intended, needs a rewrite
    @Rule(
            desc = "Prevents update suppression from working! Original concept by: Carpet-TCTC-Addition",
            category = {CARPETFIXES,BUGFIX,CRASHFIX}
    )
    public static boolean updateSuppressionFix = false;*/

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
            desc = "Fixes enderman not updating the block they place correctly",
            extra = "Fixes [MC-183054](https://bugs.mojang.com/browse/MC-183054)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean endermanDontUpdateOnPlaceFix = false;

    //by FX - PR0CESS
    @Rule(
            desc = "Fixes rails updating other rails before checking if they are in a valid location",
            extra = "Fixes [MC-174864](https://bugs.mojang.com/browse/MC-174864)",
            category = {CARPETFIXES,BUGFIX,EXPERIMENTAL}
    )
    public static boolean railInvalidUpdateOnPushFix = false;

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

    //By Hendrix-Shen
    @Rule(
            desc = "Tracing the target to another dimension does not stop checking for visibility, so that many unnecessary chunks are loaded",
            extra = "Fixes [MC-202249](https://bugs.mojang.com/browse/MC-202249)",
            category = {CARPETFIXES,BUGFIX,CRASHFIX}
    )
    public static boolean zombiePiglinTracingFix = false;


    /*

    BACKPORTS
    Bugs that are no longer Unresolved that we reintroduce into the game
    or Bugs that where fixed in the snapshots that we backport to older versions

     */

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes target blocks being permanently powered when moved by pistons",
            extra = "Fixes [MC-173244](https://bugs.mojang.com/browse/MC-173244)",
            category = {CARPETFIXES,BACKPORT}
    )
    public static boolean targetBlockPermanentlyPoweredFix = true;

    //By Hendrix-Shen
    @Rule(
            desc = "Shulkers do not teleport correctly when going through a portal.",
            extra = "Fixed [MC-139265](https://bugs.mojang.com/browse/MC-139265) from 21w03a",
            category = {CARPETFIXES,BUGFIX,BACKPORT}
    )
    public static boolean shulkerTeleportFix = false;

    //By Fallen-Breath
    @Rule(
            desc = "Fixes Drowned navigation causing memory leak/performance degradation",
            extra = "Fixed [MC-202246](https://bugs.mojang.com/browse/MC-202246) from 20w45a",
            category = {CARPETFIXES,BACKPORT,BUGFIX}
    )
    public static boolean drownedMemoryLeakFix = false;

    //By Copetan from lunaar-carpet-addons
    @Rule(
            desc = "Backport dropping the contents of a Shulker Box item when its item entity is destroyed",
            extra = "Fixed [MC-176615](https://bugs.mojang.com/browse/MC-176615) from 20w51a",
            category = {CARPETFIXES,BUGFIX,BACKPORT}
    )
    public static boolean shulkerBoxItemsDropContents = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Backport 1.12 flint and steel behavior. Flint and steel can be used for updating observers / buds",
            extra = "Fixed [MC-4923](https://bugs.mojang.com/browse/MC-4923) from 18w05a",
            category = {CARPETFIXES,BUGFIX,BACKPORT}
    )
    public static boolean oldFlintAndSteelBehavior = false;

    //By whoImT from carpet-addons
    @Rule(
            desc = "Re-introduces multiplayer donkey/llama dupe bug based on disconnecting while riding donkey/llama",
            extra = "Fixed [MC-181241](https://bugs.mojang.com/browse/MC-181241) from 18w05a",
            category = {CARPETFIXES,BUGFIX,BACKPORT}
    )
    public static boolean donkeyRidingDupeFix = true;


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

}

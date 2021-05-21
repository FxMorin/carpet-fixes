package carpetfixes;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.EXPERIMENTAL;

public class CarpetFixesSettings {

    private final static String CARPETFIXES = "carpet-fixes";
    private final static String BACKPORT = "backport"; //A bug that got fixed which we don't want fixed or came from a snapshot
    private final static String WONTFIX = "wontfix"; //Marked as `won't fix` on the bug tracker

    //Marked as `Works as Intended` on the bug tracker. I don't like these, usually will only implement for backports
    // It's not Vanilla if you aren't playing the game as the developers intended it to be played :thonk:
    private final static String INTENDED = "intended";

    //Don't include BUGFIX if the bug is not marked as Unresolved

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
    @Rule(
            desc = "Fixes Chunk Regen due to StringTag writeUTF() not respecting readUTF() Limits",
            extra = "Fixes ChunkRegen & [MC-134892](https://bugs.mojang.com/browse/MC-134892)",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean chunkRegenFix = false;

    //By FX - PR0CESS
    @Rule(
            desc = "Fixes incorrect block collision checks",
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
            category = {CARPETFIXES, BUGFIX, EXPERIMENTAL}
    )
    public static boolean repeaterPriorityFix = false;


    /*

    FROM OTHER CARPET EXTENSIONS

     */

    //by Fallen-Breath, in Carpet-TIS-Addition
    @Rule(
            desc = "Fixes rails duplicating",
            category = {CARPETFIXES,BUGFIX}
    )
    public static boolean railDuplicationFix = false;

    //by Fallen-Breath, in Carpet-TIS-Addition
    @Rule(
            desc = "Disable TNT, carpet and part of rail dupers",
            extra = {
                    "Attachment block update based dupers will do nothing and redstone component update based dupers can no longer keep their duped block",
                    "Implementation by Carpet-TIS-Addition - Dupe bad dig good"
            },
            category = {CARPETFIXES, BUGFIX, EXPERIMENTAL}
    )
    public static boolean pistonDupingFix = false;

}

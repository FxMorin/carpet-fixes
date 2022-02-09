package carpetfixes.helpers;

public class RuleCategory {
    public static final String VANILLA = "vanilla"; //Does not change any game behaviours
    public static final String RECOMMENDED = "recommended"; //Game fixes which do not fix much although are recommended. Most recommended just fix stupid bugs that are annoying
    public static final String CRASHFIX = "crashfix"; // For bugs that fix possible server crashes
    public static final String REINTRODUCE = "reintroduce"; //A bug that got fixed which we don't want fixed or a "feature" from a snapshot that was removed
    public static final String DUPE = "dupe"; //These are dupe bugs, normal bugs except there duping related.

    public static final String ADVANCED = "advanced"; //These options are for more advanced users. Allowing more flexibility!

    public static final String PARITY = "parity"; //These are parity bugs, this is a meme category.
}

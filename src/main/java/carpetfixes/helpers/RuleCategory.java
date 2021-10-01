package carpetfixes.helpers;

public class RuleCategory {
    public static final String CARPETFIXES = "carpet-fixes";
    public static final String VANILLA = "vanilla"; //Does not change any game behaviours
    public static final String RECOMMENDED = "recommended"; //Game fixes which do not fix much although are recommended. Most recommended just fix stupid bugs that are annoying
    public static final String CRASHFIX = "crashfix"; // For bugs that fix possible server crashes
    public static final String BACKPORT = "backport"; //A bug that got fixed which we don't want fixed or came from a snapshot
    public static final String WONTFIX = "wontfix"; //Marked as `won't fix` on the bug tracker

    //All crashFix's are recommended if they are not experimental!

    // Marked as `Works as Intended` on the bug tracker. I don't like these, usually will only implement for backports
    // It's not Vanilla if you aren't playing the game as the developers intended it to be played :thonk:
    // Although sometimes, its just stupid that its not fixed. So we fix it anyways, hence why its here.
    public static final String INTENDED = "intended";
}

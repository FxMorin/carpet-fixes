package carpetfixes.settings;

import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import carpet.settings.Validator;

import java.io.PrintStream;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomSettingsManager extends SettingsManager {

    private final String fancyName;
    private final BugManager bugManager;

    public CustomSettingsManager(BugManager manager,String version, String identifier, String modName) {
        super(version, identifier, modName);
        this.bugManager = manager;
        this.fancyName = modName;
    }

    /**
     * This currently adds the bugs to the wiki page. Although removes them from the carpet
     * text rules display. Since I am not able to access that code without carpet making a
     * change. Here is the todo list due to me waiting on carpet:
     *
     * TODO: ADD WONTFIX & WAI categories automatically based on bug categories
     * TODO: ADD REINTRODUCE category if bug reintroduce is true
     * TODO: ADD BUGFIX category automatically if @Bug is present and reintroduce is false
     * TODO: Stop faking to print the text over in BugManager, and actually print it for rules
     * TODO: Make sure to check for the conditional-mixins once we merge. We want to display them separately at the bottom
     */

    /**
     * A method to pretty print in markdown (useful for Github wiki/readme) the current
     * registered rules for a category to the log, like {@link SettingsManager#printAllRulesToLog}
     * Although with a twist, this one also adds the bug information to the output.
     * @param category A {@link String} being the category to print, {@link null} to print
     *                 all registered rules.
     * @return {@link int} 1 if things didn't fail, and all the info to System.out
     */
    @Override
    public int printAllRulesToLog(String category) {
        PrintStream ps = System.out;
        ps.println("# "+ fancyName +" Settings");
        for (ParsedRule<?> rule : this.getRules()) {
            if (category != null && !rule.categories.contains(category)) continue;
            ps.println("## " + rule.name);
            ps.println(rule.description+"  ");
            for (String extra : rule.extraInfo) ps.println(extra + "  ");
            ParsedBug bug = this.bugManager.getBug(rule.name);
            if (bug != null) {
                if (bug.reIntroduce) {
                    ps.println("Relates to: " + bug.reports.stream()
                            .map(ss -> {
                                String s = ss.toUpperCase(Locale.ROOT);
                                return "[" + s + "](https://bugs.mojang.com/browse/" + s + ")";
                            }).collect(Collectors.joining(", ")) + "  "
                    );
                } else {
                    ps.println("Fixes: " + bug.reports.stream()
                            .map(ss -> {
                                String s = ss.toUpperCase(Locale.ROOT);
                                return "[" + s + "](https://bugs.mojang.com/browse/" + s + ")";
                            }).collect(Collectors.joining(", ")) + "  "
                    );
                }
                ps.println("Related to: " + bug.related.stream()
                        .map(ss -> {
                            String s = ss.toUpperCase(Locale.ROOT);
                            return "[" + s + "](https://bugs.mojang.com/browse/" + s + ")";
                        }).collect(Collectors.joining(", ")) + "  "
                );
            }
            ps.println("* Type: `" + rule.type.getSimpleName() + "`  ");
            ps.println("* Default value: `" + rule.defaultAsString + "`  ");
            String optionString = rule.options.stream()
                    .map(s -> "`" + s + "`")
                    .collect(Collectors.joining(", "));
            if (!optionString.isEmpty())
                ps.println((rule.isStrict?"* Required":"* Suggested")+" options: " + optionString + "  ");
            ps.println("* Categories: " + rule.categories.stream()
                    .map(s -> "`" + s.toUpperCase(Locale.ROOT) + "`")
                    .collect(Collectors.joining(", ")) + "  ");
            boolean preamble = false;
            for (Validator<?> validator : rule.validators) {
                if(validator.description() != null) {
                    if (!preamble) {
                        ps.println("* Additional notes:  ");
                        preamble = true;
                    }
                    ps.println("  * "+validator.description()+"  ");
                }
            }
            ps.println("  ");
        }
        return 1;
    }
}

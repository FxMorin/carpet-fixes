package carpetfixes.settings;

import carpet.settings.ParsedRule;
import carpet.utils.Translations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static carpet.utils.Translations.tr;

;

public class BugManager {

    private final Map<String, ParsedBug> bugs = new HashMap<>();

    public BugManager() {}

    public ParsedBug getBug(String name) {
        return bugs.get(name);
    }

    public void parseSettingsClass(CustomSettingsManager manager, Class settingsClass, boolean isPrinter) {
        for (Field f : settingsClass.getDeclaredFields()) {
            Bug bug = f.getAnnotation(Bug.class);
            if (bug == null) continue;
            ParsedBug parsed = new ParsedBug(f, bug, this);
            bugs.put(parsed.name, parsed);
            if (!isPrinter) {
                ParsedRule<?> rule = manager.getRule(parsed.name);
                if (rule != null) {
                    if (parsed.reports.size() > 0) {
                        if (parsed.reIntroduce) {
                            System.out.println("Reverts: " + parsed.reports.stream()
                                    .map(ss -> {
                                        String s = ss.toUpperCase(Locale.ROOT);
                                        return "[" + s + "](https://bugs.mojang.com/browse/" + s + ")";
                                    }).collect(Collectors.joining(", ")));
                        } else {
                            System.out.println("Fixes: " + parsed.reports.stream()
                                    .map(ss -> {
                                        String s = ss.toUpperCase(Locale.ROOT);
                                        return "[" + s + "](https://bugs.mojang.com/browse/" + s + ")";
                                    }).collect(Collectors.joining(", ")));
                        }
                    }
                    if (parsed.related.size() > 0) {
                        System.out.println("Related to: " + parsed.related.stream()
                                .map(ss -> {
                                    String s = ss.toUpperCase(Locale.ROOT);
                                    return "[" + s + "](https://bugs.mojang.com/browse/" + s + ")";
                                }).collect(Collectors.joining(", ")));
                    }
                }
            }
        }
    }

    /**
     * @return A {@link String} being the translated {@link ParsedBug#categories} of this
     * 	                        {@link ParsedBug}, in Carpet's configured language.
     */
    public static String translatedLabel(String label) {
        String key = String.format("bug.labels.%s", label);
        return Translations.hasTranslation(key) ? tr(key) + String.format(" (%s)", label): label;
    }

    /**
     * @return A {@link String} being the translated {@link ParsedBug#resolution} of this
     * 	                        {@link ParsedBug}, in Carpet's configured language.
     */
    public static String translatedResolution(String res) {
        String key = String.format("bug.resolutions.%s", res);
        return Translations.hasTranslation(key) ? tr(key) + String.format(" (%s)", res): res;
    }
}

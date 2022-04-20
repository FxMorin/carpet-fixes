package carpetfixes.settings;

import carpet.settings.*;
import carpetfixes.CFSettings;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.OPTIMIZATION;
import static carpetfixes.helpers.RuleCategory.*;

public class CustomSettingsManager extends SettingsManager {

    private final String fancyName;

    public CustomSettingsManager(String version, String identifier, String modName) {
        super(version, identifier, modName);
        this.fancyName = modName;
    }

    /**
     * Custom implementation to include custom conditions version predicate
     */
    @Override
    public int printAllRulesToLog(String category) {
        PrintStream ps = System.out;
        ps.println("# "+ fancyName +" Settings");
        int totalRules = 0;
        int totalFixes = 0;
        int totalOptimizations = 0;
        int totalDupes = 0;
        int totalReIntroduced = 0;
        int totalParity = 0;
        int totalCrash = 0;
        int totalAdvanced = 0;
        int toBeRemoved = 0;
        for (Field f : CFSettings.class.getDeclaredFields()) {
            Rule rule = f.getAnnotation(Rule.class);
            if (rule == null) continue;
            totalRules++;
            String defaultAsString = "";
            try {
                defaultAsString = convertToString(f.get(null));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            boolean isStrict = rule.strict();
            List<String> categories = List.of(rule.category());
            if (category != null && !categories.contains(category)) continue;
            if (category == null) {
                if (categories.contains(BUGFIX)) totalFixes++;
                if (categories.contains(OPTIMIZATION)) totalOptimizations++;
                if (categories.contains(DUPE)) totalDupes++;
                if (categories.contains(REINTRODUCE)) totalReIntroduced++;
                if (categories.contains(PARITY)) totalParity++;
                if (categories.contains(CRASHFIX)) totalCrash++;
                if (categories.contains(ADVANCED)) totalAdvanced++;
            }
            ps.println("## " + (rule.name().isEmpty() ? f.getName() : rule.name()));
            ps.println(rule.desc()+"  ");
            for (String extra : List.of(rule.extra())) {
                if (extra != null) {
                    if (extra.startsWith("[MC-")) extra = "Fixes: "+extra;
                    ps.println(
                            extra.replace("Warning!", "**Warning!**")
                            + "  "
                    );
                }
            }
            Class<?> type = f.getType();
            ps.println("* Type: `" + type.getSimpleName() + "`  ");
            ps.println("* Default value: `" + defaultAsString + "`  ");
            List<String> options;
            if (rule.options().length > 0) {
                options = List.of(rule.options());
            } else if (type == boolean.class) {
                options = List.of("true","false");
            } else if(type == String.class && categories.contains(RuleCategory.COMMAND)) {
                options = List.of("true", "false", "ops");
            } else if (type.isEnum()) {
                options = Arrays.stream(type.getEnumConstants()).map(e ->
                        ((Enum<?>) e).name().toLowerCase(Locale.ROOT)).toList();
            } else {
                options = List.of();
            }
            List<Validator<?>> validators = new ArrayList<>();
            for (Class<?> v : rule.validate())
                validators.add((Validator<?>) callConstructor(v));
            if (categories.contains(RuleCategory.COMMAND)) {
                validators.add(callConstructor(Validator._COMMAND.class));
                if (type == String.class) {
                    isStrict = false;
                    validators.add(callConstructor(Validator._COMMAND_LEVEL_VALIDATOR.class));
                }
            }
            if (isStrict && !options.isEmpty()) {
                if (type == boolean.class || type == int.class || type == double.class || type == float.class) {
                    validators.add(callConstructor(Validator._STRICT_IGNORECASE.class));
                } else {
                    validators.add(callConstructor(Validator._STRICT.class));
                }
            }
            String optionString = options.stream()
                    .map(s -> "`" + s + "`")
                    .collect(Collectors.joining(", "));
            if (!optionString.isEmpty())
                ps.println((isStrict?"* Required":"* Suggested")+" options: " + optionString + "  ");
            ps.println("* Categories: " + categories.stream()
                    .map(s -> "`" + s.toUpperCase(Locale.ROOT) + "`")
                    .collect(Collectors.joining(", ")) + "  ");
            List<String> conditions = new ArrayList<>();
            for (Class<? extends Condition> condition : rule.condition()) {
                try {
                    Constructor<?> constr = condition.getDeclaredConstructor();
                    constr.setAccessible(true);
                    Object newInstance = constr.newInstance();
                    if (!((Condition) newInstance).isTrue()) {
                        if (newInstance instanceof VersionConditions.CustomCondition custom)
                            conditions.add(custom.getPredicate());
                    }
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conditions.size() > 0) {
                toBeRemoved++;
                StringBuilder builder = new StringBuilder();
                int c = 0;
                builder.append("* Limited to: ");
                for (String cond : conditions) {
                    if (c++ != 0) builder.append(",");
                    builder.append("`").append(cond).append("`");
                }
                ps.println(builder);
            }
            boolean preamble = false;
            for (Validator<?> validator : validators) {
                if(validator.description() != null) {
                    if (!preamble) {
                        ps.println("* Additional notes:  ");
                        preamble = true;
                    }
                    ps.println("  * "+validator.description()+"  ");
                }
            }
        }
        ps.println("# Stats");
        ps.println("Rules: `"+totalRules+"`  ");
        ps.println("Fixes: `"+totalFixes+"`  ");
        ps.println("Crash Fixes: `"+totalCrash+"`  ");
        ps.println("Dupe Fixes: `"+totalDupes+"`  ");
        ps.println("ReIntroduced: `"+totalReIntroduced+"`  ");
        ps.println("Optimizations: `"+totalOptimizations+"`  ");
        ps.println("Advanced Rules: `"+totalAdvanced+"`  ");
        ps.println("Parity Fixes: `"+totalParity+"`  ");
        ps.println("Rules to be removed next big release: `"+toBeRemoved+"`");
        return 1;
    }

    private <T> T callConstructor(Class<T> cls) {
        try {
            Constructor<T> constr = cls.getDeclaredConstructor();
            constr.setAccessible(true);
            return constr.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertToString(Object value) {
        if (value instanceof Enum) return ((Enum<?>) value).name().toLowerCase(Locale.ROOT);
        return value.toString();
    }
}

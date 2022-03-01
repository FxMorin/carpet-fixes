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
        for (Field f : CFSettings.class.getDeclaredFields()) {
            Rule rule = f.getAnnotation(Rule.class);
            if (rule == null) continue;
            String defaultAsString = "";
            try {
                defaultAsString = convertToString(f.get(null));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            boolean isStrict = rule.strict();
            List<String> categories = rule.category() != null ? List.of(rule.category()) : List.of();
            if (!categories.contains(category)) continue;
            ps.println("## " + (rule.name().isEmpty() ? f.getName() : rule.name()));
            ps.println(rule.desc()+"  ");
            for (String extra : List.of(rule.extra())) ps.println(extra + "  ");
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
                StringBuilder builder = new StringBuilder();
                int c = 0;
                builder.append("Limited to: ");
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

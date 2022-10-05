package carpetfixes.helpers;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RuleScheduler {

    /*
     * Some rules need world to be loaded before being able to apply there settings
     * This class will hold a list of rules that should be set once the world is loaded
     */

    private static final List<RuleChange<?>> scheduledRuleChanges = new ArrayList<>();

    public RuleScheduler() {}

    public void addRule(RuleChange<?> ruleChange) {
        scheduledRuleChanges.add(ruleChange);
    }

    /*
     * Adds a rule that needs to be called once the validator is set
     */
    public <T> void addRule(Validator<T> validator, CarpetRule<T> currentRule, T newValue, String string) {
        addRule(new RuleChange<>(validator, currentRule, newValue, string));
    }

    /*
     * Adds a rule that should be set to a default value which requires world. We do this by passing in a function
     * that will get the default value that should be used for the rule. It's a bit of a workaround, although
     * carpet does not really provide any better alternative.
     * The string value should be ignored for default values!
     */
    public <T> void addDefaultRule(Validator<T> validator, CarpetRule<T> currentRule, T newValue, String string, Function<MinecraftServer,T> setDefault) {
        addRule(new RuleChange<>(validator, currentRule, newValue, string, setDefault));
    }

    public void onWorldLoaded(MinecraftServer server) {
        for (RuleChange<?> ruleChange : scheduledRuleChanges)
            ruleChange.triggerValidator(server);
        scheduledRuleChanges.clear();
    }

    static class RuleChange<T> {
        private final Validator<T> validator;
        private final CarpetRule<T> currentRule;
        private final T newValue;
        private final String string;
        private final Function<MinecraftServer,T> setDefault;
        public RuleChange(Validator<T> validator, CarpetRule<T> currentRule, T newValue, String string, Function<MinecraftServer,T> setDefault) {
            this.validator = validator;
            this.currentRule = currentRule;
            this.newValue = newValue;
            this.string = string;
            this.setDefault = setDefault;
        }

        public RuleChange(Validator<T> validator, CarpetRule<T> currentRule, T newValue, String string) {
            this(validator, currentRule, newValue, string, null);
        }

        public void triggerValidator(MinecraftServer server) {
            T newValue = this.newValue;
            if (this.setDefault != null)
                newValue = this.setDefault.apply(server);
            this.validator.validate(server.getCommandSource(), this.currentRule, newValue, this.string);
        }
    }
}

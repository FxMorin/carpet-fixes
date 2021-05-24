package carpetfixes.helpers;

import carpet.CarpetServer;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.command.ServerCommandSource;

public class RulePresets {

    public static void runChangePresetRule(ServerCommandSource source, CarpetFixesSettings.PresetSettings newValue) {
        switch(newValue) {
            case VANILLA:
                RulePresets.setVanilla(source);
                break;
            case BACKPORT:
                RulePresets.setBackport(source);
                break;
            case CRASHFIX:
                RulePresets.setCrashFix(source);
                break;
            case STABILITY:
                RulePresets.setStability(source);
                break;
            case NOTBACKPORTS:
                RulePresets.setNotBackport(source);
                break;
            case ALL:
                RulePresets.setAll(source);
                break;
            case CUSTOM:
            default:
        }
    }

    public static boolean isStabilityRuleException(String name) {
        //List of rules which should count as stability even tho they don't fit the requirements
        return name.equals("drownedMemoryLeakFix"); // || ...
    }

    public static void setAll(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset") && rule.isDefault()) {
                rule.set(source, Boolean.toString(!rule.getBoolValue()));
            }
        }
    }

    public static void setNotBackport(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if ((rule.categories.contains("backport") && !rule.isDefault()) || rule.isDefault()) {
                    rule.set(source,  Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setCrashFix(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if ((rule.categories.contains("crashfix") && rule.isDefault()) || !rule.isDefault()) {
                    rule.set(source,  Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setBackport(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if ((rule.categories.contains("backport") && rule.isDefault()) || !rule.isDefault()) {
                    rule.set(source,  Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setVanilla(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (!rule.isDefault()) {
                    rule.set(source,  Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setStability(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (rule.isDefault() && rule.categories.contains("crashfix") || (!rule.categories.contains("backport") && !rule.categories.contains("experimental")) || isStabilityRuleException(rule.name) || !rule.isDefault()) {
                    rule.set(source,  Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }
}

package carpetfixes.helpers;

import carpet.CarpetServer;
import net.minecraft.server.command.ServerCommandSource;

public class RulePresets {

    public static boolean isStabilityRuleException(String name) {
        //List of rules which should count as stability even tho they don't fit the rules
        return name.equals("drownedMemoryLeakFix"); // || ...
    }

    public static void setAll(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset") && rule.isDefault()) {
                rule.set(source, rule.getBoolValue() ? "false" : "true");
            }
        }
    }

    public static void setNotBackport(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (rule.categories.contains("backport") && !rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                } else if (rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                }
            }
        }
    }

    public static void setCrashFix(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (rule.categories.contains("crashfix") && rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                } else if (!rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                }
            }
        }
    }

    public static void setBackport(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (rule.categories.contains("backport") && rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                } else if (!rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                }
            }
        }
    }

    public static void setVanilla(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (!rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                }
            }
        }
    }

    public static void setStability(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains("carpet-fixes") && !rule.name.equals("carpetFixesPreset")) {
                if (rule.isDefault() && rule.categories.contains("crashfix") || (!rule.categories.contains("backport") && !rule.categories.contains("experimental")) || isStabilityRuleException(rule.name)) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                } else if (!rule.isDefault()) {
                    rule.set(source,  rule.getBoolValue() ? "false" : "true");
                }
            }
        }
    }
}

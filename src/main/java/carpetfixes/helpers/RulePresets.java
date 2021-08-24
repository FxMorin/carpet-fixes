package carpetfixes.helpers;

import carpet.CarpetServer;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.EXPERIMENTAL;
import static carpetfixes.helpers.RuleCategory.*;

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
        return false; //name.equals("drownedMemoryLeakFix"); // || ...
    }

    public static void setAll(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains(CARPETFIXES) && !rule.name.equals("carpetFixesPreset") && rule.isDefault()) {
                rule.set(source, Boolean.toString(!rule.getBoolValue()));
            }
        }
    }

    public static void setNotBackport(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains(CARPETFIXES) && !rule.name.equals("carpetFixesPreset")) {
                if ((rule.categories.contains(BACKPORT) && !rule.isDefault()) || rule.isDefault()) {
                    rule.set(source, Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setCrashFix(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains(CARPETFIXES) && !rule.name.equals("carpetFixesPreset")) {
                if ((rule.categories.contains(CRASHFIX) && rule.isDefault()) || !rule.isDefault()) {
                    rule.set(source, Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setBackport(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains(CARPETFIXES) && !rule.name.equals("carpetFixesPreset")) {
                if ((rule.categories.contains(BACKPORT) && rule.isDefault()) || !rule.isDefault()) {
                    rule.set(source, Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setVanilla(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains(CARPETFIXES) && !rule.name.equals("carpetFixesPreset")) {
                if (!rule.isDefault()) {
                    rule.set(source, Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }

    public static void setStability(ServerCommandSource source) {
        for (carpet.settings.ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            if (rule.categories.contains(CARPETFIXES) && !rule.name.equals("carpetFixesPreset")) {
                if (rule.isDefault() && rule.categories.contains(CRASHFIX) || (!rule.categories.contains(BACKPORT) && !rule.categories.contains(EXPERIMENTAL)) || isStabilityRuleException(rule.name) || !rule.isDefault()) {
                    rule.set(source, Boolean.toString(!rule.getBoolValue()));
                }
            }
        }
    }
}

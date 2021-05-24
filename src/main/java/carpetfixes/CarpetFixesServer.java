package carpetfixes;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetfixes.helpers.RulePresets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

public class CarpetFixesServer implements CarpetExtension, ModInitializer {

    public static boolean initialPreset = true;

    @Override
    public String version()
    {
        return "carpetfixes";
    }

    @Override
    public void onInitialize() { CarpetServer.manageExtension(new CarpetFixesServer()); }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetFixesSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        if (initialPreset) {
            initialPreset = false;
            RulePresets.runChangePresetRule(CarpetServer.minecraft_server.getCommandSource(), (CarpetFixesSettings.PresetSettings) CarpetServer.settingsManager.getRule("carpetFixesPreset").get());
        }
    }

    @Override
    public void onServerLoadedWorlds(MinecraftServer minecraftServer) {
        CarpetServer.settingsManager.addRuleObserver(((source, rule, s) -> {
            if (rule.name.equals("carpetFixesPreset")) {
                RulePresets.runChangePresetRule(CarpetServer.minecraft_server.getCommandSource(), (CarpetFixesSettings.PresetSettings) rule.get());
            } else if (!source.getName().equals("Server") && rule.categories.contains("carpet-fixes") && !CarpetServer.settingsManager.getRule("carpetFixesPreset").equals(CarpetFixesSettings.PresetSettings.CUSTOM)) {
                CarpetServer.settingsManager.getRule("carpetFixesPreset").set(source, "custom");
            }
        }));
    }
}

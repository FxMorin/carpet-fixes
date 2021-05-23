package carpetfixes;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class CarpetFixesServer implements CarpetExtension, ModInitializer {

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
        CarpetServer.settingsManager.addRuleObserver(((source, rule, s) -> {
            if (!rule.name.equals("carpetFixesPreset") && rule.categories.contains("carpet-fixes") && !CarpetServer.settingsManager.getRule("carpetFixesPreset").equals(CarpetFixesSettings.PresetSettings.CUSTOM)) {
                CarpetServer.settingsManager.getRule("carpetFixesPreset").set(source,"custom");
            }
        }));
    }
}

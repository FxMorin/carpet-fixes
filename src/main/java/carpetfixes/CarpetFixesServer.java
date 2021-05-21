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
    public void onInitialize() {
        CarpetServer.manageExtension(new CarpetFixesServer());
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetFixesSettings.class);
    }
}

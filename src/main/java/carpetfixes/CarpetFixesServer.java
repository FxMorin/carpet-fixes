package carpetfixes;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetfixes.helpers.UpdateScheduler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class CarpetFixesServer implements CarpetExtension, ModInitializer {

    @Override
    public String version()
    {
        return "carpetfixes";
    }

    @Override
    public void onInitialize() {CarpetServer.manageExtension(new CarpetFixesServer());}

    @Override
    public void onGameStarted() { CarpetServer.settingsManager.parseSettingsClass(CarpetFixesSettings.class); }

    @Override
    public void onServerLoaded(MinecraftServer server) {}

    @Override
    public void onServerLoadedWorlds(MinecraftServer minecraftServer) {
        for (ServerWorld world : minecraftServer.getWorlds()) { CarpetFixesInit.updateScheduler.put(world,new UpdateScheduler(world));}
    }
}

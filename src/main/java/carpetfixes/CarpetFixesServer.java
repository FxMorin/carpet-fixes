package carpetfixes;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;
import carpetfixes.helpers.UpdateScheduler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.border.WorldBorder;

public class CarpetFixesServer implements CarpetExtension, ModInitializer {

    private static final SettingsManager carpetFixesSettingsManager;

    private static final String MOD_ID = "carpet-fixes";
    private static final String MOD_NAME;
    private static final Version MOD_VERSION;

    public static String modId() {return MOD_ID;}

    public static String modName()
    {
        return MOD_NAME;
    }

    static {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();
        MOD_NAME = metadata.getName();
        MOD_VERSION = metadata.getVersion();
        carpetFixesSettingsManager = new SettingsManager(MOD_VERSION.getFriendlyString(),MOD_ID,MOD_NAME);
    }

    @Override
    public String version()
    {
        return MOD_VERSION.getFriendlyString();
    }

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new CarpetFixesServer());
    }

    @Override
    public void onGameStarted() {
        carpetFixesSettingsManager.parseSettingsClass(CarpetFixesSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {}

    @Override
    public void onServerLoadedWorlds(MinecraftServer minecraftServer) {
        for (ServerWorld world : minecraftServer.getWorlds()) { CarpetFixesSettings.updateScheduler.put(world,new UpdateScheduler(world));}
    }

    @Override
    public void onTick(MinecraftServer server) {
        if (CarpetFixesSettings.scheduleWorldBorderReset) {
            CarpetFixesSettings.scheduleWorldBorderReset = false;
            WorldBorder worldBorder = server.getOverworld().getWorldBorder();
            worldBorder.setSize(worldBorder.getSize()); //This will force the worldborder to update its voxelShape cache
        }
    }

    @Override
    public SettingsManager customSettingsManager() {
        return carpetFixesSettingsManager;
    }

    public static SettingsManager getCarpetFixesSettingsManager() {
        return carpetFixesSettingsManager;
    }
}

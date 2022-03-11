package carpetfixes;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;
import carpetfixes.helpers.UpdateScheduler;
import carpetfixes.settings.CustomSettingsManager;
import carpetfixes.testing.tests.TestRuleTemplate;
import mctester.annotation.TestRegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.border.WorldBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarpetFixesServer implements CarpetExtension, ModInitializer {

    private static final SettingsManager carpetFixesSettingsManager;

    public static final Logger LOGGER = LoggerFactory.getLogger(CarpetFixesServer.class);

    private static final String MOD_ID = "carpet-fixes";
    private static final String MOD_NAME;
    private static final Version MOD_VERSION;

    public static String modId() {
        return MOD_ID;
    }

    public static String modName() {
        return MOD_NAME;
    }

    static {
        ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID)
                .orElseThrow(RuntimeException::new).getMetadata();
        MOD_NAME = metadata.getName();
        MOD_VERSION = metadata.getVersion();
        carpetFixesSettingsManager = new CustomSettingsManager(MOD_VERSION.getFriendlyString(),MOD_ID,MOD_NAME);
    }

    @Override
    public String version() {
        return MOD_VERSION.getFriendlyString();
    }

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new CarpetFixesServer());
        if (SharedConstants.isDevelopment) {
            TestRegistryHelper.createTestTemplateFromClass(TestRuleTemplate.class);
            TestRegistryHelper.createTemplatedTestsFromFiles();
        }
    }

    @Override
    public void onGameStarted() {
        carpetFixesSettingsManager.parseSettingsClass(CFSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {}

    @Override
    public void onServerLoadedWorlds(MinecraftServer minecraftServer) {
        for (ServerWorld world : minecraftServer.getWorlds()) {
            CFSettings.updateScheduler.put(world,new UpdateScheduler(world));
        }
    }

    @Override
    public void onTick(MinecraftServer server) {
        if (CFSettings.scheduleWorldBorderReset) {
            CFSettings.scheduleWorldBorderReset = false;
            WorldBorder worldBorder = server.getOverworld().getWorldBorder();
            worldBorder.setSize(worldBorder.getSize()); //Forces the worldborder to update its voxelShape cache
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

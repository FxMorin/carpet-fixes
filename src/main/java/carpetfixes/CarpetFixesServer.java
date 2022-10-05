package carpetfixes;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.api.settings.SettingsManager;
import carpet.utils.Translations;
import carpetfixes.helpers.RuleScheduler;
import carpetfixes.settings.CustomSettingsManager;
import carpetfixes.testing.tests.TestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.SharedConstants;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.border.WorldBorder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class CarpetFixesServer implements CarpetExtension, ModInitializer {

    private static final SettingsManager carpetFixesSettingsManager;
    public static final RuleScheduler ruleScheduler;
    public static boolean areWorldsLoaded;

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
        areWorldsLoaded = false;
        carpetFixesSettingsManager = new CustomSettingsManager(MOD_VERSION.getFriendlyString(), MOD_ID, MOD_NAME);
        ruleScheduler = new RuleScheduler();
    }

    @Override
    public String version() {
        return MOD_VERSION.getFriendlyString();
    }

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new CarpetFixesServer());
        if (SharedConstants.isDevelopment) TestManager.initializeTests();
    }

    @Override
    public void onGameStarted() {
        carpetFixesSettingsManager.parseSettingsClass(CFSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        areWorldsLoaded = false;
    }

    @Override
    public void onServerLoadedWorlds(MinecraftServer minecraftServer) {
        areWorldsLoaded = true;
        ruleScheduler.onWorldLoaded(minecraftServer);
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
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, final CommandRegistryAccess commandBuildContext) {
        carpetFixesSettingsManager.registerCommand(dispatcher, commandBuildContext);
    }

    public static SettingsManager getCarpetFixesSettingsManager() {
        return carpetFixesSettingsManager;
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return Translations.getTranslationFromResourcePath("assets/carpet-fixes/lang/%s.json".formatted(lang));
    }
}

package carpetfixes.settings;

import carpetfixes.CarpetFixesServer;
import carpetfixes.CarpetFixesSettings;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.PrintStream;

/**
 * From Fabric-Carpet
 * Runs only from GitHub Actions to generate the wiki
 * page with all Carpet rules on it.
 * It also now includes carpet-fixes's custom bug manager for more detailed rules
 */
public class RulePrinter implements DedicatedServerModInitializer, PreLaunchEntrypoint {
    public static final PrintStream OLD_OUT = System.out;

    @Override
    public void onInitializeServer() {
        // Minecraft (or whatever) changes the System.out to have prefixes,
        // our simple parser doesn't like that. So we change it back
        System.setOut(OLD_OUT);
        CustomSettingsManager settings = CarpetFixesServer.getCarpetFixesSettingsManager();
        BugManager bugs = CarpetFixesServer.getCarpetFixesBugManager();
        settings.parseSettingsClass(CarpetFixesSettings.class);
        bugs.parseSettingsClass(settings,CarpetFixesSettings.class,true);
        settings.printAllRulesToLog(null);
        System.exit(0);
    }

    @Override
    public void onPreLaunch() {
        // Just initializes our OLD_OUT by nooping the class
    }
}
package carpetfixes.helpers;

import carpetfixes.CarpetFixesServer;
import carpetfixes.CarpetFixesSettings;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.PrintStream;
import java.lang.System;

/**
 * From Fabric-Carpet
 * Runs only from GitHub Actions to generate the wiki
 * page with all Carpet rules on it.
 * It is here so it can be managed by the IDE
 */
public class CarpetRulePrinter implements DedicatedServerModInitializer, PreLaunchEntrypoint {
    public static final PrintStream OLD_OUT = System.out;

    @Override
    public void onInitializeServer() {
        // Minecraft (or whatever) changes the System.out to have prefixes,
        // our simple parser doesn't like that. So we change it back
        System.setOut(OLD_OUT);
        CarpetFixesServer.getCarpetFixesSettingsManager().parseSettingsClass(CarpetFixesSettings.class);
        CarpetFixesServer.getCarpetFixesSettingsManager().printAllRulesToLog(null);
        System.exit(0);
    }

    @Override
    public void onPreLaunch() {
        // Just initializes our OLD_OUT by nooping the class
    }
}
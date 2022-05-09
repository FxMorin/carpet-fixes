package carpetfixes.settings;

import carpet.settings.Condition;
import net.fabricmc.loader.api.FabricLoader;

public class Conditions {
    public static class ConflictTechReborn implements Condition {
        @Override
        public boolean isTrue() {
            return !FabricLoader.getInstance().isModLoaded("techreborn");
        }
    }
}

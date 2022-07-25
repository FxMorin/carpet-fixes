package carpetfixes.settings;

import me.fallenbreath.conditionalmixin.api.mixin.ConditionTester;
import net.fabricmc.loader.api.FabricLoader;

public class ModPredicates {

    private static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static class VMPConditionalPredicate implements ConditionTester {
        @Override
        public boolean isSatisfied(String mixinClassName) {
            return isModLoaded("vmp") || isModLoaded("c2me-opts-chunkio");
        }
    }
}

package carpetfixes.settings;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CarpetFixesMixinConfigPlugin extends RestrictiveMixinConfigPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarpetFixesMixinConfigPlugin.class);

    public static final Version MINECRAFT_VERSION = FabricLoader.getInstance()
            .getModContainer("minecraft").orElseThrow().getMetadata().getVersion();


    @Override
    protected void onRestrictionCheckFailed(String mixinClassName, String reason) {
        LOGGER.info("[Carpet-Fixes] Disabled mixin {} due to {}", mixinClassName, reason);
    }

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mi) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mi) {}
}
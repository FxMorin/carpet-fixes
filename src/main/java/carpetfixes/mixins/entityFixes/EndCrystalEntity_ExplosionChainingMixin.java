package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Just bypass the isExplosionCheck, I can tell Mojang didn't want that much recursion within the explosion which
 * is why it's not doing it. Although they would need to implement an explosion list or entity ticking of sorts
 * which I might end up adding at some point.
 */

@Mixin(EndCrystalEntity.class)
public class EndCrystalEntity_ExplosionChainingMixin {

    @Redirect(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean isExplosiveBypass(DamageSource source, TagKey tagKey) {
        return !CFSettings.crystalExplodeOnExplodedFix && source.isIn(tagKey);
    }
}

package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntity_tridentStriderMixin {


    @Redirect(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;" +
                            "getDepthStrider(Lnet/minecraft/entity/LivingEntity;)I"
            )
    )
    private int modifyDepthStriderIfUsingRiptide(LivingEntity entity) {
        if (CFSettings.depthStriderSlowsRiptideFix && entity.isUsingRiptide()) return 0;
        return EnchantmentHelper.getDepthStrider(entity);
    }
}

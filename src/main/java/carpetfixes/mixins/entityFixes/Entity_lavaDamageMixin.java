package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Entity.class)
public class Entity_lavaDamageMixin {

    @Shadow public World world;


    @Inject(
            method = "isFireImmune()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isFireImmuneAndServerSide(CallbackInfoReturnable<Boolean> cir) {
        if (CarpetFixesSettings.lavaDamageDesyncFix && this.world.isClient()) cir.setReturnValue(true);
    }
}

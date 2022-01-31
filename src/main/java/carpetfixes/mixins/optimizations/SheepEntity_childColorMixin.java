package carpetfixes.mixins.optimizations;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepEntity_childColorMixin {


    @Inject(
            method = "getChildColor(Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/AnimalEntity;)Lnet/minecraft/util/DyeColor;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getChildColor(AnimalEntity p1, AnimalEntity p2, CallbackInfoReturnable<DyeColor> cir) {
        if (CarpetFixesSettings.optimizedRecipeManager) {
            cir.setReturnValue(Utils.properDyeMixin(((SheepEntity)p1).getColor(),((SheepEntity)p2).getColor()));
        }
    }
}

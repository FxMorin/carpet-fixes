package carpetfixes.mixins.optimizations;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntity_childColorMixin extends AnimalEntity {

    protected SheepEntity_childColorMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "getChildColor(Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/AnimalEntity;)Lnet/minecraft/util/DyeColor;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getChildColor(AnimalEntity firstParent, AnimalEntity secondParent, CallbackInfoReturnable<DyeColor> cir) {
        if (CFSettings.optimizedRecipeManager) {
            DyeColor firstColor = ((SheepEntity)firstParent).getColor();
            DyeColor secondColor = ((SheepEntity)secondParent).getColor();
            DyeColor col = Utils.properDyeMixin(firstColor,secondColor);
            if (col == null) col = this.world.random.nextBoolean() ? firstColor : secondColor;
            cir.setReturnValue(col);
        }
    }
}

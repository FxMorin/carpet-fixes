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

/**
 * The game determines the child sheep's color by getting a wool block from the parents, putting them in a crafting
 * recipe, getting the output wool and getting the color from that.
 * I don't know in what world we would consider a data-driven method with that much overhead as a smart idea. Instead,
 * we used a prebaked list of all the possible colors and combinations, however this means that you can't use a
 * datapack to change it.
 */

@Mixin(SheepEntity.class)
public abstract class SheepEntity_childColorMixin extends AnimalEntity {

    protected SheepEntity_childColorMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "getChildColor(Lnet/minecraft/entity/passive/AnimalEntity;" +
                    "Lnet/minecraft/entity/passive/AnimalEntity;)Lnet/minecraft/util/DyeColor;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getChildColor(AnimalEntity firstParent, AnimalEntity secondParent,
                               CallbackInfoReturnable<DyeColor> cir) {
        if (CFSettings.optimizedRecipeManager) {
            DyeColor firstColor = ((SheepEntity)firstParent).getColor();
            DyeColor secondColor = ((SheepEntity)secondParent).getColor();
            DyeColor col = Utils.properDyeMixin(firstColor,secondColor);
            if (col == null) col = this.getWorld().random.nextBoolean() ? firstColor : secondColor;
            cir.setReturnValue(col);
        }
    }
}

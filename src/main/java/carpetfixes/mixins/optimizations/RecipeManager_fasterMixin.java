package carpetfixes.mixins.optimizations;

import carpetfixes.CFSettings;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipeManager_fasterMixin {

    @Shadow
    protected abstract <C extends Inventory, T extends Recipe<C>> Map<Identifier, Recipe<C>>
    getAllOfType(RecipeType<T> type);


    @SuppressWarnings("unchecked")
    @Inject(
            method = "getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/inventory/Inventory;" +
                    "Lnet/minecraft/world/World;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true
    )
    public <C extends Inventory, T extends Recipe<C>> void getFirstMatch(RecipeType<T> type, C inventory, World world,
                                                                         CallbackInfoReturnable<Optional<T>> cir) {
        if (CFSettings.optimizedRecipeManager) {
            int slots = 0;
            for (int slot = 0;slot < inventory.size(); slot++) {
                if (!inventory.getStack(slot).isEmpty()) slots++;
            }
            //compare size to quickly remove recipes that are not even close. Plus remove streams
            for (Recipe<C> recipe : this.getAllOfType(type).values()) {
                if (recipe.getIngredients().size() == slots && recipe.matches(inventory,world)) {
                    cir.setReturnValue((Optional<T>)Optional.of(recipe));
                    return;
                }
            }
            cir.setReturnValue(Optional.empty());
        }
    }
}

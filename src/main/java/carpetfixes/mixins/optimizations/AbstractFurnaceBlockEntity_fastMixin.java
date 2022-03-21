package carpetfixes.mixins.optimizations;

import carpetfixes.CFSettings;
import carpetfixes.helpers.EventManager;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntity_fastMixin {

    /**
     * This optimization for furnaces is drastically faster than most furnace caching/optimization mods
     * What we do is we cache the last recipe used, since this singular check against the last recipe is nearly
     * negligible. The cache in return drastically improves the performance of large automated smelting & smelting
     * more than a single item are a time in a furnace. Resulting in a performance boost basically everywhere since
     * who smelts one item at a time, and if you are than it's not enough to lag a server.
     * Secondly this optimization also caches the fuelTimeMap, a map that can only change on a datapack reload...
     * so I cache it, and refresh it on a datapack reload. Before the game would create a LinkedHashMap every single
     * time that it
     */

    private static Optional<Recipe<Inventory>> lastRecipe = Optional.empty();
    private static Map<Item, Integer> cachedFuelTimeMap = null;

    static {
        EventManager.addEventListener(
                EventManager.CF_Event.DATAPACK_RELOAD,
                () -> {
                    lastRecipe = Optional.empty();
                    cachedFuelTimeMap = null;
                }
        );
    }


    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/recipe/RecipeManager;getFirstMatch(Lnet/minecraft/recipe/RecipeType;" +
                            "Lnet/minecraft/inventory/Inventory;Lnet/minecraft/world/World;)Ljava/util/Optional;"
            )
    )
    private static Optional<Recipe<Inventory>> getFirstMatchAndCache(RecipeManager manager,
                                                                     RecipeType<Recipe<Inventory>> type,
                                                                     Inventory inventory, World world) {
        if (CFSettings.optimizedFurnaces) {
            if(lastRecipe.isPresent() && lastRecipe.get().matches(inventory, world)) return lastRecipe;
            return lastRecipe = manager.getFirstMatch(type, inventory, world);
        }
        return manager.getFirstMatch(type, inventory, world);
    }


    @Inject(
            method = "getCookTime",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType,
                                    Inventory inventory, CallbackInfoReturnable<Integer> cir) {
        if (CFSettings.optimizedFurnaces) {
            if(lastRecipe.isPresent() && lastRecipe.get() instanceof AbstractCookingRecipe cookingRecipe &&
                    cookingRecipe.matches(inventory, world)) {
                cir.setReturnValue(cookingRecipe.getCookTime());
            } else {
                cir.setReturnValue(
                        world.getRecipeManager()
                                .getFirstMatch(recipeType, inventory, world)
                                .map(AbstractCookingRecipe::getCookTime)
                                .orElse(200)
                );
            }
        }
    }


    @Inject(
            method = "createFuelTimeMap()Ljava/util/Map;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void useCachedFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        if (cachedFuelTimeMap != null) cir.setReturnValue(cachedFuelTimeMap);
    }


    @Inject(
            method = "createFuelTimeMap()Ljava/util/Map;",
            at = @At("RETURN")
    )
    private static void cacheFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        if (CFSettings.optimizedFurnaces && cachedFuelTimeMap == null) {
            cachedFuelTimeMap = cir.getReturnValue();
        }
    }
}

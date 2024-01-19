package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Predicate;

/**
 * Fixes hoppers choosing minecarts randomly every time
 */
@Mixin(HopperBlockEntity.class)
public class HopperBlockEntity_randomMinecartMixin {

    @Unique
    private static final Predicate<Entity> VALID_NOT_EMPTY_INVENTORIES = entity ->
            entity.isAlive() && entity instanceof Inventory inv && !inv.isEmpty();


    @ModifyArg(
            method = "getInventoryAt(Lnet/minecraft/world/World;DDD)Lnet/minecraft/inventory/Inventory;",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/block/ChestBlock;getInventory(" +
                                    "Lnet/minecraft/block/ChestBlock;Lnet/minecraft/block/BlockState;" +
                                    "Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Z)" +
                                    "Lnet/minecraft/inventory/Inventory;",
                            shift = At.Shift.AFTER,
                            ordinal = 0
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;",
                    ordinal = 0
            ),
            index = 2
    )
    private static Predicate<? super Entity> cf$modifyPredicate(Predicate<? super Entity> predicate) {
        return CFSettings.hoppersSelectMinecartsRandomlyFix ? VALID_NOT_EMPTY_INVENTORIES : predicate;
    }
}

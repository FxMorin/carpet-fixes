package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.WitherSkullBlock;
import net.minecraft.block.pattern.CachedBlockPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(WitherSkullBlock.class)
public class WitherSkullBlock_spawningMixin {

    /**
     * Allows replaceable materials to be within the wither structure
     * when the wither is spawning.
     */


    @Redirect(
            method = "getWitherBossPattern()Lnet/minecraft/block/pattern/BlockPattern;",
            at = @At(
                    value="INVOKE",
                    target="Lnet/minecraft/block/pattern/CachedBlockPosition;matchesBlockState(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;",
                    ordinal=1
            ))
    private static Predicate<CachedBlockPosition> replaceableMaterialPredicate(Predicate<BlockState> state){
        if (CarpetFixesSettings.witherGolemSpawningFix) state = CarpetFixesSettings.IS_REPLACEABLE;
        return CachedBlockPosition.matchesBlockState(state);
    }


    @Redirect(
            method = "getWitherDispenserPattern()Lnet/minecraft/block/pattern/BlockPattern;",
            at = @At(
                    value="INVOKE",
                    target="Lnet/minecraft/block/pattern/CachedBlockPosition;matchesBlockState(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;"
            ))
    private static Predicate<CachedBlockPosition> replaceableMaterialPredicateDispenser(Predicate<BlockState> state){
        if (CarpetFixesSettings.witherGolemSpawningFix) state = CarpetFixesSettings.IS_REPLACEABLE;
        return CachedBlockPosition.matchesBlockState(state);
    }
}

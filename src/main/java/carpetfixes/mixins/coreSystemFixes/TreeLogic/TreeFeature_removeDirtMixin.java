package carpetfixes.mixins.coreSystemFixes.TreeLogic;

import carpetfixes.CarpetFixesInit;
import carpetfixes.CarpetFixesSettings;
import com.google.common.collect.Lists;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(TreeFeature.class)
public class TreeFeature_removeDirtMixin {


    @Redirect(
            require=0,
            method= "placeLogsAndLeaves(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockBox;Ljava/util/Set;Ljava/util/Set;)Lnet/minecraft/util/shape/VoxelSet;",
            at=@At(
                    value="INVOKE",
                    target="Lcom/google/common/collect/Lists;newArrayList(Ljava/lang/Iterable;)Ljava/util/ArrayList;",
                    ordinal = 1
            ))
    private static <E> ArrayList<E> modifySet(Iterable<? extends E> elements) {
        ArrayList<E> list = Lists.newArrayList(elements);
        if (CarpetFixesSettings.treeTrunkLogicFix) {
            CarpetFixesInit.lastDirt.forEach(list::remove);
            CarpetFixesInit.lastDirt.clear();
            return list;
        }
        CarpetFixesInit.lastDirt.clear();
        return list;
    }
}

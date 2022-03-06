package carpetfixes.mixins.other;

import carpet.CarpetSettings;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.test.StructureTestUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructureTestUtil.class)
public class StructureTestUtils_updatesMixin {


    @ModifyExpressionValue(
            method = "createStructure(Ljava/lang/String;Lnet/minecraft/server/world/ServerWorld;)" +
                    "Lnet/minecraft/structure/Structure;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/structure/StructureManager;createStructure(" +
                            "Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/structure/Structure;"
            )
    )
    private static Structure wrapCreateStructure(StructureManager structureManager, NbtCompound nbt) {
        if (SharedConstants.isDevelopment) {
            CarpetSettings.impendingFillSkipUpdates.set(true);
            try {
                return structureManager.createStructure(nbt);
            } finally {
                CarpetSettings.impendingFillSkipUpdates.set(false);
            }
        } else {
            return structureManager.createStructure(nbt);
        }
    }
}

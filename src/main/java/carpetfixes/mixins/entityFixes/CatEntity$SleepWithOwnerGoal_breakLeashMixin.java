package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.entity.passive.CatEntity$SleepWithOwnerGoal")
public class CatEntity$SleepWithOwnerGoal_breakLeashMixin {
    @Shadow @Final private CatEntity cat;

    /**
     * Basically before doing the teleport check if the cat is on a lead
     * If so, then teleport it around the lead so that we don't accidentally
     * break the lead and risk someone's pet cat escaping
     */
    @Redirect(method = "dropMorningGifts()V", at = @At(value="INVOKE",target="Lnet/minecraft/util/math/BlockPos$Mutable;set(Lnet/minecraft/util/math/Vec3i;)Lnet/minecraft/util/math/BlockPos$Mutable;", ordinal=0))
    public BlockPos.Mutable SetCorrectly(BlockPos.Mutable mutable, Vec3i pos) {
        if (CarpetFixesSettings.catsBreakLeadsDuringGiftFix && this.cat.isLeashed()) {
            pos = this.cat.getHoldingEntity().getBlockPos();
        }
        return mutable.set(pos);
    }
}

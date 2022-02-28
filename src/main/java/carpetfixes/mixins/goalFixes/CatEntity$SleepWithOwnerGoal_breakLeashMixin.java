package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
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

    /**
     * Basically cats can break there leads since they teleport within 10 blocks around the player when gifting.
     * The fix is pretty easy, if the cat is on a lead than teleport it around the lead so that we don't accidentally
     * break the lead and risk someone's pet cat escaping.
     */


    @Shadow
    @Final
    private CatEntity cat;


    @Redirect(
            method = "dropMorningGifts()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos$Mutable;set(Lnet/minecraft/util/math/Vec3i;)" +
                            "Lnet/minecraft/util/math/BlockPos$Mutable;",
                    ordinal = 0
            )
    )
    public BlockPos.Mutable SetCorrectly(BlockPos.Mutable mutable, Vec3i pos) {
        if (CFSettings.catsBreakLeadsDuringGiftFix && this.cat.isLeashed() && this.cat.getHoldingEntity() != null)
            pos = this.cat.getHoldingEntity().getBlockPos();
        return mutable.set(pos);
    }
}

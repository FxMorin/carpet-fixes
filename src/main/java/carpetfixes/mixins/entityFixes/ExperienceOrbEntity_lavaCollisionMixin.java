package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntity_lavaCollisionMixin extends Entity {

    /**
     * Experience orbs doing something called a `pop` when they touch lava. Basically the experience pops out of the
     * lava right before it dies. The problem is that the pop only checks if it's in a lava block, and the pop gives
     * upwards velocity. So if the experience orb touches a flowing lava block, it does the pop effect before it
     * actually touches lava. Making it so that the experience orb never burns in flowing lava. We fix this by
     * literally copying the water code, and instead of using if in lava source block. Check if the experience orb's
     * eye height is in lava before doing the pop.
     */


    public ExperienceOrbEntity_lavaCollisionMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Redirect(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;"
            )
    )
    public FluidState betterCollision(World world, BlockPos pos) {
        return (!CFSettings.xpOrbCollisionFix || this.isSubmergedIn(FluidTags.LAVA)) ?
                this.world.getFluidState(this.getBlockPos()) :
                Fluids.EMPTY.getDefaultState();
    }
}

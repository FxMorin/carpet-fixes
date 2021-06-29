package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
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
    public ExperienceOrbEntity_lavaCollisionMixin(EntityType<?> type, World world) { super(type, world); }

    @Redirect(method= "tick()V",at=@At(value="INVOKE",target="Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;"))
    public FluidState betterCollision(World world, BlockPos pos) {
        return (!CarpetFixesSettings.xpOrbCollisionFix || this.isSubmergedIn(FluidTags.LAVA)) ? this.world.getFluidState(this.getBlockPos()) : Fluids.EMPTY.getDefaultState();
    }
}

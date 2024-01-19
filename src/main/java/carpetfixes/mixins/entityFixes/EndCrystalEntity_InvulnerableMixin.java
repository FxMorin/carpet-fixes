package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonSpawnState;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes being able to create invulnerable end crystals
 */

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntity_InvulnerableMixin extends Entity {

    @Shadow
    public abstract void setBeamTarget(@Nullable BlockPos beamTarget);

    @Shadow
    @Nullable
    public abstract BlockPos getBeamTarget();

    public EndCrystalEntity_InvulnerableMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void cf$onTick(CallbackInfo ci) {
        if (CFSettings.invulnerableEndCrystalFix && this.isInvulnerable() && this.getBeamTarget() != null) {
            ServerWorld serverWorld = (ServerWorld)this.getWorld();
            if (this.getWorld().getRegistryKey() != World.END || serverWorld.getEnderDragonFight() == null ||
                    serverWorld.getEnderDragonFight().dragonSpawnState == null ||
                    serverWorld.getEnderDragonFight().dragonSpawnState.ordinal() >
                            EnderDragonSpawnState.SUMMONING_DRAGON.ordinal()
            ){
                this.setInvulnerable(false);
                this.setBeamTarget(null);
            }
        }
    }
}

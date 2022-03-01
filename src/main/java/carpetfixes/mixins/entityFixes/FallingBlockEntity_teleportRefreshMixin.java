package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.ModIds;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Restriction(require = @Condition(value = ModIds.MINECRAFT, versionPredicates = VersionPredicates.LT_22w03a))
@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_teleportRefreshMixin extends Entity {

    @Shadow
    public int timeFalling;

    public FallingBlockEntity_teleportRefreshMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Override
    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        if (CFSettings.fallingBlockTeleportingFix && this.timeFalling == 0) {
            this.world.removeBlock(this.getBlockPos(), false);
            this.timeFalling = 1;
        }
        this.setPos(x, y, z);
        this.setYaw(yaw);
        this.setPitch(pitch);
        this.resetPosition();
        this.refreshPosition();
    }
}

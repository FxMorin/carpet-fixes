package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_teleportRefreshMixin extends Entity {

    @Shadow public int timeFalling;

    public FallingBlockEntity_teleportRefreshMixin(EntityType<?> type, World world) {super(type, world);}

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

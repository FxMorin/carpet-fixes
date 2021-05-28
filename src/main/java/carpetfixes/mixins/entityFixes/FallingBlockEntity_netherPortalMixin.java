package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntity_netherPortalMixin extends Entity {

    @Shadow
    protected void initDataTracker() {}
    @Shadow protected void readCustomDataFromTag(CompoundTag tag) {}
    @Shadow protected void writeCustomDataToTag(CompoundTag tag) {}
    @Shadow public Packet<?> createSpawnPacket() { return null; }

    public FallingBlockEntity_netherPortalMixin(EntityType<?> type, World world) { super(type, world); }

    @Inject(method= "tick()V",at=@At("HEAD"))
    public void tickNetherPortal(CallbackInfo ci) {
        if (CarpetFixesSettings.fallingBlocksCantUseNetherPortalsFix) {
            this.tickNetherPortal();
        }
    }
}

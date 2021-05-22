package carpetfixes.mixins.drownedMemoryLeak;

import carpetfixes.helpers.IDrownedEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedEntity.class)
public abstract class DrownedEntity_drownedMemoryLeakMixin extends ZombieEntity implements IDrownedEntity {
    private EntityNavigation initialEntityNavigation;

    public DrownedEntity_drownedMemoryLeakMixin(World world) { super(world); }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void recordInitialNavigation(EntityType<? extends DrownedEntity> entityType, World world, CallbackInfo ci) {
        this.initialEntityNavigation = this.getNavigation();
    }

    @Override
    public EntityNavigation getInitialEntityNavigation() {
        return this.initialEntityNavigation;
    }
}

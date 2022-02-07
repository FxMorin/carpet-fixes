package carpetfixes.mixins.conversionFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntity_conversionMixin extends MobEntity implements Monster {

    /**
     * Slime convert into smaller slimes when killed. When they die, they do not transfer
     * all the correct data to the new entities. The fix is simply to transfer the missing
     * information over to the new entity.
     */


    //Since slime is not a full conversion and instead splits into multiple entities
    //PortalCooldown, Rotation, effects, & Health are ignored
    //DeathLootTable & tags still need to be implemented!


    protected SlimeEntity_conversionMixin(EntityType<? extends MobEntity> entityType, World world) {super(entityType, world);}


    @Inject(
            method = "remove",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/mob/SlimeEntity;refreshPositionAndAngles(DDDFF)V",
                shift = At.Shift.AFTER
            )
    )
    public void ConversionFixSlime(RemovalReason reason, CallbackInfo ci, int i, Text text, boolean bl, float f, int j, int k, int l, float g, float h, SlimeEntity slimeEntity) {
        if (CarpetFixesSettings.conversionFix) {
            slimeEntity.setFireTicks(this.getFireTicks()); //Fire
            slimeEntity.setVelocity(this.getVelocity()); //Motion
            slimeEntity.setNoGravity(this.hasNoGravity()); //noGravity
            slimeEntity.setSilent(this.isSilent()); //Silent
            slimeEntity.setLeftHanded(this.isLeftHanded()); //Left Handed
        }
    }
}
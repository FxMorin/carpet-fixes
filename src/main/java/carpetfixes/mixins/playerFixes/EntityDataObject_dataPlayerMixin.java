package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.command.EntityDataObject;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EntityDataObject.class)
public class EntityDataObject_dataPlayerMixin {

    /**
     * After a lot of testing and reading the code, I don't see why we are still not allowed
     * to modify the player data directly with /data & /execute store
     * So this rule re-enables the ability to modify the player data.
     * I will make this rule experimental for now, if anyone finds any problems with it. Please inform me!
     */

    @Shadow
    @Final
    private Entity entity;


    @Inject(
            method = "setNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void setNbt(NbtCompound nbt, CallbackInfo ci) {
        if (CFSettings.unableToModifyPlayerDataFix) {
            UUID uUID = this.entity.getUuid();
            this.entity.readNbt(nbt);
            this.entity.setUuid(uUID);
            ci.cancel();
        }
    }
}

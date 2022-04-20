package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_dimensionStatusMixin extends PlayerEntity {

    @Shadow
    public ServerPlayNetworkHandler networkHandler;

    @Shadow
    private int syncedExperience;

    @Shadow
    private float syncedHealth;

    @Shadow
    private int syncedFoodLevel;

    public ServerPlayerEntity_dimensionStatusMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    // Make sure to use the synced values instead of sending the values directly so the correct value can be updated!


    @Inject(
            method = "teleport(Lnet/minecraft/server/world/ServerWorld;DDDFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;" +
                            "sendPlayerStatus(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void tpToWorld(ServerWorld world, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
        if (CFSettings.crossDimensionTeleportLosesStatsFix) {
            this.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(this.getAbilities()));
            for(StatusEffectInstance effect : this.getStatusEffects())
                this.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(this.getId(), effect));
            this.syncedExperience = -1;
            this.syncedHealth = -1.0F;
            this.syncedFoodLevel = -1;
            ci.cancel();
        }
    }
}

package carpetfixes.mixins.entityFixes;

import org.spongepowered.asm.mixin.Mixin;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/*
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*/

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntity_spectatorMixin extends PlayerEntity {

    /**
     * This "should" move the player up by `0.1905132581` when switching from survival
     * to spectator. I need to finish this xD
     */


    //@Shadow @Final public ServerPlayerInteractionManager interactionManager;
    public ServerPlayerEntity_spectatorMixin(World world, BlockPos pos, float yaw, GameProfile profile) {super(world, pos, yaw, profile);}


    //Failed attempt at fixing bug MC-146582
    /*
    @Inject(method= "changeGameMode(Lnet/minecraft/world/GameMode;)Z",at=@At("HEAD"))
    public void changeGameMode(GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        if (this.interactionManager.getGameMode() == GameMode.SPECTATOR && gameMode != GameMode.SPECTATOR) {
            this.setPosition(this.getX(), this.getY()+0.1905132581, this.getZ());
        }
    }*/
}

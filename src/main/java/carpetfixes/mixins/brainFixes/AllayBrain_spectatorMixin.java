package carpetfixes.mixins.brainFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.AllayBrain;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(AllayBrain.class)
public class AllayBrain_spectatorMixin {


    @Redirect(
            method = "method_43093(Lnet/minecraft/entity/LivingEntity;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;of(Ljava/lang/Object;)Ljava/util/Optional;"
            )
    )
    private static <T> Optional<ServerPlayerEntity> makeSurePlayerNotInSpectator(T value) {
        ServerPlayerEntity player = (ServerPlayerEntity)value;
        return CFSettings.allaySeesSpectatorsFix && player.isSpectator() ? Optional.empty() : Optional.of(player);
    }
}

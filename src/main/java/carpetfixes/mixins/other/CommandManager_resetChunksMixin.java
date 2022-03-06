package carpetfixes.mixins.other;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ResetChunksCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManager_resetChunksMixin {

    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;


    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/command/ReloadCommand;" +
                            "register(Lcom/mojang/brigadier/CommandDispatcher;)V"
            )
    )
    private void onInit(CommandManager.RegistrationEnvironment environment, CallbackInfo ci) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) ResetChunksCommand.register(this.dispatcher);
    }
}

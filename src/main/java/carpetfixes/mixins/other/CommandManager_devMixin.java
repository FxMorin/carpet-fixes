package carpetfixes.mixins.other;

import carpetfixes.testing.commands.FillSummonCommand;
import carpetfixes.testing.commands.PoiCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManager_devMixin {

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
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ResetChunksCommand.register(this.dispatcher);
            ChaseCommand.register(this.dispatcher);
            DebugMobSpawningCommand.register(this.dispatcher);
            DebugPathCommand.register(this.dispatcher);
            RaidCommand.register(this.dispatcher);
            PoiCommand.register(this.dispatcher); //Custom
            FillSummonCommand.register(this.dispatcher); //Custom
        }
    }
}

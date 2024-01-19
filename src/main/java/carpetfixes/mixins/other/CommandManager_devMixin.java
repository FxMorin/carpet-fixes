package carpetfixes.mixins.other;

import carpetfixes.testing.commands.FillSummonCommand;
import carpetfixes.testing.commands.PoiCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Add all of Mojang's debug commands to minecraft if you are in a development environment.
 * Also adds my own custom commands
 */

@Mixin(CommandManager.class)
public class CommandManager_devMixin {

    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;


    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void cf$onInit(CommandManager.RegistrationEnvironment environment,
                           CommandRegistryAccess arg, CallbackInfo ci) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ResetChunksCommand.register(this.dispatcher);
            ChaseCommand.register(this.dispatcher);
            DebugMobSpawningCommand.register(this.dispatcher);
            DebugPathCommand.register(this.dispatcher);
            RaidCommand.register(this.dispatcher);
            PoiCommand.register(this.dispatcher); //Custom
            FillSummonCommand.register(this.dispatcher, arg); //Custom
        }
    }
}

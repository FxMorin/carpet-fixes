package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The sea level is hardcoded to 64 in multiple places in the code. This is an issue since you are supposed to be
 * able to change the sea level through datapacks. The fix is to make the seaLevel method get the seaLevel from
 * the chunk generator. Since the chunk generator uses the value from the datapacks.
 */

@Mixin(World.class)
public abstract class World_seaLevelMixin implements WorldAccess  {

    @Shadow
    @Nullable
    public abstract MinecraftServer getServer();

    @Shadow
    @Final
    private RegistryKey<World> registryKey;


    @Inject(
            method = "getSeaLevel()I",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void cf$getSeaLevel(CallbackInfoReturnable<Integer> cir) {
        if (CFSettings.hardcodedSeaLevelFix)
            cir.setReturnValue(
                    this.getServer().getWorld(this.registryKey).getChunkManager().getChunkGenerator().getSeaLevel()
            );
    }
}

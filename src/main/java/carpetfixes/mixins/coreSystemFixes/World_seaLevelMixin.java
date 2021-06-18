package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class World_seaLevelMixin implements WorldAccess  {
    @Shadow @Nullable public abstract MinecraftServer getServer();

    @Shadow @Final private RegistryKey<World> registryKey;

    @Inject(method= "getSeaLevel()I",at=@At("HEAD"),cancellable = true)
    public void getSeaLevel(CallbackInfoReturnable<Integer> cir) {
        if (CarpetFixesSettings.hardcodedSeaLevelFix) {
            cir.setReturnValue(this.getServer().getWorld(this.registryKey).getChunkManager().getChunkGenerator().getSeaLevel());
        }
    }
}

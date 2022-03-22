package carpetfixes.mixins.accessors;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.block.NeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {
    @Accessor("neighborUpdater")
    void setNeighborUpdater(NeighborUpdater updater);

}

package carpetfixes.mixins.accessors;

import net.minecraft.class_7165;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {
    @Accessor("field_37279")
    void setNeighborUpdater(class_7165 updater);

}

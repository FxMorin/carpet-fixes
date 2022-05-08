package carpetfixes.mixins.accessors;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;
import java.util.UUID;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("random")
    Random getRandom();
    @Accessor("uuid")
    void setUuid(UUID uuid);
}

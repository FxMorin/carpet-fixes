package carpetfixes.mixins.accessors;

import net.minecraft.entity.Entity;
import net.minecraft.world.gen.random.AbstractRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("random")
    AbstractRandom getRandom();
    @Accessor("uuid")
    void setUuid(UUID uuid);
}

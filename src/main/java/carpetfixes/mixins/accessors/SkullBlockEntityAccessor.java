package carpetfixes.mixins.accessors;

import net.minecraft.block.entity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkullBlockEntity.class)
public interface SkullBlockEntityAccessor {
    @Accessor("powered")
    boolean getPowered();
    @Accessor("powered")
    void setPowered(boolean powered);
    @Accessor("ticksPowered")
    int getTicksPowered();
    @Accessor("ticksPowered")
    void setTicksPowered(int ticks);
}

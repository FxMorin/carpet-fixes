package carpetfixes.mixins.accessors;

import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractPressurePlateBlock.class)
public interface AbstractPressurePlateBlockAccessor {
    @Accessor("BOX") @Mutable
    static void setBox(Box BOX) {
        throw new AssertionError();
    }
}

package carpetfixes.mixins.entityFixes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessorMixin
{
    @Accessor("dataTracker")
    DataTracker accessorGetDataTracker();

    @Accessor("world")
    World accessorGetWorld();

    @Invoker("getX")
    double invokerGetX();

    @Invoker("getY")
    double invokerGetY();

    @Invoker("getZ")
    double invokerGetZ();

    @Invoker("isSneaking")
    boolean invokerIsSneaking();
}
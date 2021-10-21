package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets="net/minecraft/world/chunk/WorldChunk$DirectBlockEntityTickInvoker")
public abstract class WorldChunk$DirectBlockEntityTickInvoker_illegalBlockEntityMixin <T extends BlockEntity> implements BlockEntityTickInvoker {

    @Shadow @Final private T blockEntity;


    @Redirect(
            method="tick",
            at=@At(
                    value="INVOKE",
                    target="Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;[Lorg/apache/logging/log4j/util/Supplier;)V"
            ))
    public void dontWarnInsteadRemove(Logger logger, String message, Supplier<?>[] paramSuppliers) {
        if (CarpetFixesSettings.illegalBlockEntityFix) {
            blockEntity.getWorld().getChunk(blockEntity.getPos()).removeBlockEntity(blockEntity.getPos());
        } else {
            logger.warn(message,paramSuppliers);
        }
    }
}

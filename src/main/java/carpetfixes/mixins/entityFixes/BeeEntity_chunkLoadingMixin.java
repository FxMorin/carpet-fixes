package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntity_chunkLoadingMixin extends AnimalEntity {


    @Shadow
    @Nullable
    BlockPos hivePos;


    protected BeeEntity_chunkLoadingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method= "isHiveNearFire",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockEntity(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/entity/BlockEntity;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void isHiveNearFireAndLoaded(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.beeDupeFix && !this.world.isChunkLoaded(
                ChunkSectionPos.getSectionCoord(this.hivePos.getX()),
                ChunkSectionPos.getSectionCoord(this.hivePos.getY())
        )) cir.setReturnValue(true);
    }


    @Inject(
            method= "doesHiveHaveSpace",
            at = @At("HEAD"),
            cancellable = true
    )
    public void doesHiveHaveSpaceAndLoaded(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.beeDupeFix && (this.hivePos == null || !this.world.isChunkLoaded(
                ChunkSectionPos.getSectionCoord(pos.getX()),
                ChunkSectionPos.getSectionCoord(pos.getY())
        ))) cir.setReturnValue(false);
    }


    @Inject(
            method= "isHiveValid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockEntity(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/entity/BlockEntity;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void isHiveValidAndLoaded(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.beeDupeFix && !this.world.isChunkLoaded(
                ChunkSectionPos.getSectionCoord(this.hivePos.getX()),
                ChunkSectionPos.getSectionCoord(this.hivePos.getY())
        )) cir.setReturnValue(false);
    }
}

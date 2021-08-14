package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatSpawner.class)
public class CatSpawner_incorrectCatMixin {

    /**
     * Cats in witch huts that spawn with world gen will sometimes not be a black cat.
     * This is due to the cat not being initialized in the right order. We simply
     * make sure the initializations are done in the right order to prevent this.
     */


    @Inject(
            method= "spawn(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/server/world/ServerWorld;)I",
            at=@At("HEAD"),
            cancellable = true
    )
    private void spawn(BlockPos pos, ServerWorld world, CallbackInfoReturnable<Integer> cir) {
        if (CarpetFixesSettings.witchHutsSpawnIncorrectCatFix) {
            CatEntity catEntity = EntityType.CAT.create(world);
            if (catEntity != null) {
                catEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                catEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, (EntityData) null, (NbtCompound) null);
                world.spawnEntityAndPassengers(catEntity);
                cir.setReturnValue(1);
            }
            cir.setReturnValue(0);
        }
    }
}

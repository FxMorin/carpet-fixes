package carpetfixes.mixins.brainFixes;

import carpetfixes.CFSettings;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.FrogBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.HashSet;
import java.util.Set;

@Mixin(FrogBrain.class)
public class FrogBrain_temptJumpMixin {

    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cachedList = null;


    @ModifyArg(
            method = "addLongJumpActivities(Lnet/minecraft/entity/ai/brain/Brain;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/brain/Brain;setTaskList(" +
                            "Lnet/minecraft/entity/ai/brain/Activity;" +
                            "Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"
            ),
            index = 2
    )
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> modifyRequiredLaySpawnActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        if (CFSettings.frogJumpsIgnoreTemptedFix) {
            if (cachedList == null) {
                Set<Pair<MemoryModuleType<?>, MemoryModuleState>> newMemories = new HashSet<>(requiredMemories);
                newMemories.add(Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT));
                cachedList = newMemories;
            }
            return cachedList;
        }
        return requiredMemories;
    }
}

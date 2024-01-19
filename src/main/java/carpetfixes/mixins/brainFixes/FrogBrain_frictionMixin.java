package carpetfixes.mixins.brainFixes;

import carpetfixes.CFSettings;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.FrogBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

/**
 * For an in-depth explanation please check GoatBrain_frictionMixin.java
 *
 * Any reason why the sets are not constants?
 */

@Mixin(FrogBrain.class)
public class FrogBrain_frictionMixin {

    @Unique
    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$idleFrogRequiredMemories = ImmutableSet.of(
            Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_PRESENT),
            Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)
    );

    @Unique
    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$swimFrogRequiredMemories = ImmutableSet.of(
            Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_PRESENT),
            Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_PRESENT)
    );

    @Unique
    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$layFrogRequiredMemories = ImmutableSet.of(
            Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_PRESENT),
            Pair.of(MemoryModuleType.IS_PREGNANT, MemoryModuleState.VALUE_PRESENT)
    );


    @ModifyArg(
            method = "addIdleActivities(Lnet/minecraft/entity/ai/brain/Brain;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/brain/Brain;setTaskList(" +
                            "Lnet/minecraft/entity/ai/brain/Activity;" +
                            "Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"
            ),
            index = 2
    )
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$modifyRequiredIdleActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        return CFSettings.frictionlessEntitiesFix ? cf$idleFrogRequiredMemories : requiredMemories;
    }


    @ModifyArg(
            method = "addSwimActivities(Lnet/minecraft/entity/ai/brain/Brain;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/brain/Brain;setTaskList(" +
                            "Lnet/minecraft/entity/ai/brain/Activity;" +
                            "Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"
            ),
            index = 2
    )
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$modifyRequiredSwimActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        return CFSettings.frictionlessEntitiesFix ? cf$swimFrogRequiredMemories : requiredMemories;
    }


    @ModifyArg(
            method = "addLaySpawnActivities(Lnet/minecraft/entity/ai/brain/Brain;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/brain/Brain;setTaskList(" +
                            "Lnet/minecraft/entity/ai/brain/Activity;" +
                            "Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"
            ),
            index = 2
    )
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$modifyRequiredLaySpawnActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        return CFSettings.frictionlessEntitiesFix ? cf$layFrogRequiredMemories : requiredMemories;
    }
}

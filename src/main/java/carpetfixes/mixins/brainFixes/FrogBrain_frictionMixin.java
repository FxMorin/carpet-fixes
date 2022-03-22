package carpetfixes.mixins.brainFixes;

import carpetfixes.CFSettings;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.FrogBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

@Mixin(FrogBrain.class)
public class FrogBrain_frictionMixin {

    /**
     * For an in-depth explanation please check GoatBrain_frictionMixin.java
     *
     * Any reason why the sets are not constants?
     */


    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> idleFrogRequiredMemories = ImmutableSet.of(
            Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_PRESENT),
            Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)
    );

    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> swimFrogRequiredMemories = ImmutableSet.of(
            Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_PRESENT),
            Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_PRESENT)
    );

    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> layFrogRequiredMemories = ImmutableSet.of(
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
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> modifyRequiredIdleActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        return CFSettings.frictionlessEntitiesFix ? idleFrogRequiredMemories : requiredMemories;
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
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> modifyRequiredSwimActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        return CFSettings.frictionlessEntitiesFix ? swimFrogRequiredMemories : requiredMemories;
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
    private static Set<Pair<MemoryModuleType<?>, MemoryModuleState>> modifyRequiredLaySpawnActivities(
            Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
    ) {
        return CFSettings.frictionlessEntitiesFix ? layFrogRequiredMemories : requiredMemories;
    }
}

package carpetfixes.mixins.brainFixes;

import carpetfixes.CFSettings;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.GoatBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

/**
 * As explained in my code analysis here: https://bugs.mojang.com/browse/MC-228273
 * The issue is due to the IDLE task being able to run during the LONG_JUMP task. Well a very simple way to get
 * around this problem without causing any issues is actually just to check if the cooldowns are set instead of
 * checking if the other values are not set. This makes the activities exclusive to each other without having
 * to rework anything. ;)
 * LONG_JUMP_COOLING_DOWN gets set during the task, although since the LONG_JUMP task is not being cancelled
 * anymore, it still won't stop the LONG_JUMP task. If you really want to make sure, you could add:
 * `Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleState.VALUE_ABSENT)` in the list also, although
 * currently it seems to work fine without it.
 * I wouldn't recommend this as a permanent solution, and I still believe mojang should make a stricter requirement
 * system either by making activities check if other activities are running, or by setting the requirements once
 * an activity starts.
 * Thanks for coming to my Ted Talk
 */

@Mixin(GoatBrain.class)
public class GoatBrain_frictionMixin {

    @Unique
    private static final Set<Pair<MemoryModuleType<?>, MemoryModuleState>> cf$idleGoatRequiredMemories =
            ImmutableSet.of(
                    Pair.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryModuleState.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleState.VALUE_PRESENT)
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
        return CFSettings.frictionlessEntitiesFix ? cf$idleGoatRequiredMemories : requiredMemories;
    }
}

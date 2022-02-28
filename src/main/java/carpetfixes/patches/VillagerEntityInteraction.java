package carpetfixes.patches;

import net.minecraft.entity.EntityInteraction;

import java.util.UUID;

public interface VillagerEntityInteraction {
    void onInteractionWith(EntityInteraction interaction, UUID uuid);
}

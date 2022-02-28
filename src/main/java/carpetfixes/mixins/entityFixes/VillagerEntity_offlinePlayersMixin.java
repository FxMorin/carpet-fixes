package carpetfixes.mixins.entityFixes;

import carpetfixes.patches.VillagerEntityInteraction;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(VillagerEntity.class)
public class VillagerEntity_offlinePlayersMixin implements VillagerEntityInteraction {

    @Shadow
    @Final
    private VillagerGossips gossip;

    //Rewriting the method the way it should have been written to begin with

    @Override
    public void onInteractionWith(EntityInteraction interaction, UUID uuid) {
        if (interaction == EntityInteraction.ZOMBIE_VILLAGER_CURED) {
            this.gossip.startGossip(uuid, VillageGossipType.MAJOR_POSITIVE, 20);
            this.gossip.startGossip(uuid, VillageGossipType.MINOR_POSITIVE, 25);
        } else if (interaction == EntityInteraction.TRADE) {
            this.gossip.startGossip(uuid, VillageGossipType.TRADING, 2);
        } else if (interaction == EntityInteraction.VILLAGER_HURT) {
            this.gossip.startGossip(uuid, VillageGossipType.MINOR_NEGATIVE, 25);
        } else if (interaction == EntityInteraction.VILLAGER_KILLED) {
            this.gossip.startGossip(uuid, VillageGossipType.MAJOR_NEGATIVE, 25);
        }
    }
}

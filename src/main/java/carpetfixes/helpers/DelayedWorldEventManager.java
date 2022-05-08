package carpetfixes.helpers;

import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DelayedWorldEventManager {

    // This should be moved into the World's at some point

    private static final HashMap<World,List<Pair<Integer,DelayedWorldEvent>>> delayedWorldEvents = new HashMap<>();

    public static void addDelayedWorldEvent(World world, int eventId, BlockPos pos, int data) {
        addDelayedWorldEvent(world, eventId, pos, data, 1);
    }

    public static void addDelayedWorldEvent(World world, int eventId, BlockPos pos, int data, int delay) {
        delayedWorldEvents.computeIfAbsent(world,(a) -> new ArrayList<>())
                .add(new Pair<>(delay,new DelayedWorldEvent(eventId, pos, data)));
    }

    public static void tick(World world) {
        delayedWorldEvents.computeIfAbsent(world,(a) -> new ArrayList<>()).removeIf(pair -> {
            int delay = pair.getLeft() - 1;
            if (delay <= 0) {
                pair.getRight().sendWorldEvent(world);
                return true;
            } else {
                pair.setLeft(delay);
                return false;
            }
        });
    }

    static class DelayedWorldEvent {

        private final int eventId;
        private final BlockPos pos;
        private final int data;

        DelayedWorldEvent(int eventId, BlockPos pos, int data) {
            this.eventId = eventId;
            this.pos = pos;
            this.data = data;
        }

        public void sendWorldEvent(World world) {
            world.syncWorldEvent(null, this.eventId, this.pos, this.data);
        }
    }
}

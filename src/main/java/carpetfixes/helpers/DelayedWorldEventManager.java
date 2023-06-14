package carpetfixes.helpers;

import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DelayedWorldEventManager {

    // This should be moved into the World's at some point

    private static final HashMap<World,List<Pair<Integer,DelayedWorldEvent>>> delayedWorldEvents = new HashMap<>();

    public static void addDelayedWorldEvent(World world, GameEvent gameEvent, Vec3d emitterPos, GameEvent.Emitter emitter) {
        addDelayedWorldEvent(world, gameEvent, emitterPos, emitter, 1);
    }

    public static void addDelayedWorldEvent(World world, GameEvent gameEvent, Vec3d emitterPos, GameEvent.Emitter emitter, int delay) {
        delayedWorldEvents.computeIfAbsent(world,(a) -> new ArrayList<>())
                .add(new Pair<>(delay, new DelayedWorldEvent(gameEvent, emitterPos, emitter)));
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

        private final GameEvent event;
        private final Vec3d emitterPos;
        private final GameEvent.Emitter emitter;

        DelayedWorldEvent(GameEvent event, Vec3d emitterPos, GameEvent.Emitter emitter) {
            this.event = event;
            this.emitterPos = emitterPos;
            this.emitter = emitter;
        }

        public void sendWorldEvent(World world) {
            world.emitGameEvent(this.event, this.emitterPos, this.emitter);
        }
    }
}

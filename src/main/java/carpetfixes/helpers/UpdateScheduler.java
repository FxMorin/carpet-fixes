package carpetfixes.helpers;

import com.google.common.collect.Queues;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Queue;

public class UpdateScheduler {

    private final Queue<ScheduledUpdate> currentUpdates = Queues.newArrayDeque();
    private final ServerWorld world;

    public UpdateScheduler(ServerWorld world) {
        this.world = world;
    }

    public void tick() {
        Queue<ScheduledUpdate> tempUpdates = Queues.newArrayDeque();
        tempUpdates.addAll(this.currentUpdates);
        this.currentUpdates.clear();
        ScheduledUpdate nextBlockUpdate;
        while((nextBlockUpdate = tempUpdates.poll()) != null) {
            try {
                world.updateNeighbor(nextBlockUpdate.pos, nextBlockUpdate.block, nextBlockUpdate.pos);
            } catch (Throwable var7) {
                //empty
            }
        }
    }

    public void addScheduledUpdate(ScheduledUpdate update) {
        if (!this.currentUpdates.contains(update)) {
            this.currentUpdates.add(update);
            //System.out.println("["+this.currentUpdates.size()+"] ScheduleUpdate - pos: "+update.pos.toShortString());
        }
    }

    public static class ScheduledUpdate {

        private final BlockPos pos;
        private final Block block;

        public ScheduledUpdate(BlockPos pos, Block block) {
            this.pos = pos;
            this.block = block;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ScheduledUpdate com)) {
                return false;
            } else {
                return this.pos.equals(com.pos) && this.block == com.block;
            }
        }

        public int hashCode() {
            return this.pos.hashCode();
        }

        public String toString() {
            return this.block.toString() + ": " + this.pos;
        }

        public Block getBlock() {
            return this.block;
        }

        public BlockPos getPos() {
            return this.pos;
        }
    }
}

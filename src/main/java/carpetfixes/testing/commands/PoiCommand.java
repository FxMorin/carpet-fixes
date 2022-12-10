package carpetfixes.testing.commands;

import carpetfixes.mixins.accessors.ThreadedAnvilChunkStorageAccessor;
import carpetfixes.patches.RegionBasedStorageLeak;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Optional;

import static net.minecraft.server.command.CommandManager.literal;

public class PoiCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("poi").requires(source -> source.hasPermissionLevel(2))
                        .then(literal("totalChunks")
                                .executes(PoiCommand::executeTotalChunks))
                        .then(literal("get")
                                .then(CommandManager.argument("blockpos", BlockPosArgumentType.blockPos()).executes(c -> executeGet(c,false))
                                        .then(literal("visualize").executes(c -> executeGet(c,true)))))
                        .then(literal("remove")
                                .then(CommandManager.argument("blockpos", BlockPosArgumentType.blockPos()).executes(c -> executeGet(c,false))
                                        .then(literal("visualize").executes(c -> executeRemove(c,true)))))
        );
    }

    private static int executeTotalChunks(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerWorld world = source.getWorld();
        PointOfInterestStorage poiStorage = ((ThreadedAnvilChunkStorageAccessor) world.getChunkManager().threadedAnvilChunkStorage).getPoiStorage();
        int totalChunks = ((RegionBasedStorageLeak<?>)poiStorage).getTotalElements();
        source.sendFeedback(Text.of("There are currently "+totalChunks+" loaded poi chunks!"), false);
        return 0;
    }

    private static int executeGet(CommandContext<ServerCommandSource> context, boolean visualize) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        BlockPos pos = BlockPosArgumentType.getLoadedBlockPos(context, "blockpos");
        ServerWorld world = source.getWorld();
        PointOfInterestStorage poiStorage = ((ThreadedAnvilChunkStorageAccessor) world.getChunkManager().threadedAnvilChunkStorage).getPoiStorage();
        Optional<RegistryEntry<PointOfInterestType>> optionalType = poiStorage.getType(pos);
        if (optionalType.isPresent()) {
            source.sendFeedback(Text.of(optionalType.get().getType().toString()), false);
        } else {
            source.sendFeedback(Text.of("There is no POI at this position!"), false);
        }
        return 0;
    }

    private static int executeRemove(CommandContext<ServerCommandSource> context, boolean visualize) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        BlockPos pos = BlockPosArgumentType.getLoadedBlockPos(context, "blockpos");
        ServerWorld world = source.getWorld();
        PointOfInterestStorage poiStorage = ((ThreadedAnvilChunkStorageAccessor) world.getChunkManager().threadedAnvilChunkStorage).getPoiStorage();
        Optional<RegistryEntry<PointOfInterestType>> optionalType = poiStorage.getType(pos);
        if (optionalType.isPresent()) {
            source.sendFeedback(Text.of("Removed POI - "+optionalType.get()), false);
            poiStorage.remove(pos);
            DebugInfoSender.sendPoiRemoval(world, pos);
        } else {
            source.sendFeedback(Text.of("There is no POI at this position!"), false);
        }
        return 0;
    }
}

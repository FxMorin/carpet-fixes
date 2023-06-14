package carpetfixes.testing.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FillSummonCommand {

    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.failed"));
    private static final SimpleCommandExceptionType FAILED_UUID_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.invalidPosition"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                CommandManager.literal("fillsummon")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("entity", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENTITY_TYPE))
                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                .then(CommandManager.argument("fromPos", BlockPosArgumentType.blockPos())
                                        .then(CommandManager.argument("toPos", BlockPosArgumentType.blockPos())
                                                .executes(context -> execute(
                                                        context.getSource(),
                                                        RegistryEntryArgumentType.getSummonableEntityType(context, "entity"),
                                                        BlockPosArgumentType.getValidBlockPos(context, "fromPos"),
                                                        BlockPosArgumentType.getValidBlockPos(context, "toPos"),
                                                        new NbtCompound(),
                                                        true
                                                ))
                                                .then(CommandManager.argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                        .executes(context -> execute(
                                                                context.getSource(),
                                                                RegistryEntryArgumentType.getSummonableEntityType(context, "entity"),
                                                                BlockPosArgumentType.getValidBlockPos(context, "fromPos"),
                                                                BlockPosArgumentType.getValidBlockPos(context, "toPos"),
                                                                NbtCompoundArgumentType.getNbtCompound(context, "nbt"),
                                                                false
                                                        )))))));
    }

    private static int execute(ServerCommandSource source, RegistryEntry.Reference<EntityType<?>> entityType, BlockPos fromPos, BlockPos toPos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        int count = 0;
        for (BlockPos blockPos : BlockPos.iterate(fromPos,toPos)) {
            count++;
            if (!World.isValid(blockPos)) throw INVALID_POSITION_EXCEPTION.create();
            Vec3d pos = Vec3d.ofCenter(blockPos);
            NbtCompound nbtCompound = nbt.copy();
            nbtCompound.putString("id", entityType.registryKey().getValue().toString());
            ServerWorld serverWorld = source.getWorld();
            Entity entity2 = EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, entityx -> {
                entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            if (entity2 == null) throw FAILED_EXCEPTION.create();
            if (initialize && entity2 instanceof MobEntity) {
                ((MobEntity) entity2).initialize(
                        source.getWorld(),
                        source.getWorld().getLocalDifficulty(entity2.getBlockPos()),
                        SpawnReason.COMMAND,
                        null,
                        null
                );
            }
            if (!serverWorld.spawnNewEntityAndPassengers(entity2)) throw FAILED_UUID_EXCEPTION.create();
        }
        int finalCount = count;
        source.sendFeedback(() -> Text.of("Successfully summoned "+ finalCount +" "+entityType.registryKey().getValue().toString()), true);
        return 1;
    }
}

package carpetfixes.testing.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FillSummonCommand {

    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed"));
    private static final SimpleCommandExceptionType FAILED_UUID_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(
            new TranslatableText("commands.summon.invalidPosition")
    );

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("fillsummon")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.argument("entity", EntitySummonArgumentType.entitySummon())
                                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                .then(CommandManager.argument("fromPos", BlockPosArgumentType.blockPos())
                                        .then(CommandManager.argument("toPos", BlockPosArgumentType.blockPos())
                                                .executes(context -> execute(
                                                        context.getSource(),
                                                        EntitySummonArgumentType.getEntitySummon(context, "entity"),
                                                        BlockPosArgumentType.getBlockPos(context, "fromPos"),
                                                        BlockPosArgumentType.getBlockPos(context, "toPos"),
                                                        new NbtCompound(),
                                                        true
                                                ))
                                                .then(CommandManager.argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                        .executes(context -> execute(
                                                                context.getSource(),
                                                                EntitySummonArgumentType.getEntitySummon(context, "entity"),
                                                                BlockPosArgumentType.getBlockPos(context, "fromPos"),
                                                                BlockPosArgumentType.getBlockPos(context, "toPos"),
                                                                NbtCompoundArgumentType.getNbtCompound(context, "nbt"),
                                                                false
                                                        )))))));
    }

    private static int execute(ServerCommandSource source, Identifier entity, BlockPos fromPos, BlockPos toPos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        int count = 0;
        for (BlockPos blockPos : BlockPos.iterate(fromPos,toPos)) {
            count++;
            if (!World.isValid(blockPos)) throw INVALID_POSITION_EXCEPTION.create();
            Vec3d pos = Vec3d.ofCenter(blockPos);
            NbtCompound nbtCompound = nbt.copy();
            nbtCompound.putString("id", entity.toString());
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
        source.sendFeedback(Text.of("Successfully summoned "+count+" "+entity.toString()), true);
        return 1;
    }
}

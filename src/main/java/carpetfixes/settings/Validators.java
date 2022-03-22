package carpetfixes.settings;

import carpet.settings.ParsedRule;
import carpet.settings.Validator;
import carpetfixes.CFSettings;
import carpetfixes.helpers.BlockUpdateUtils;
import carpetfixes.helpers.DirectionUtils;
import carpetfixes.mixins.accessors.ServerWorldAccessor;
import carpetfixes.mixins.accessors.TagKeyAccessor;
import com.google.common.collect.Interners;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.block.ChainRestrictedNeighborUpdater;
import net.minecraft.world.block.SimpleNeighborUpdater;

import static net.minecraft.world.block.NeighborUpdater.UPDATE_ORDER;

public class Validators {
    public static class WitherGolemSpawningFixValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            ((CarvedPumpkinBlock)(Blocks.CARVED_PUMPKIN)).ironGolemPattern = null;
            ((CarvedPumpkinBlock)(Blocks.CARVED_PUMPKIN)).ironGolemDispenserPattern = null;
            return newValue;
        }
    }

    public static class WorldBorderCollisionRoundingFixValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            CFSettings.scheduleWorldBorderReset = true;
            return newValue;
        }
    }

    public static class TagKeyMemoryLeakFixValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (currentRule.get() != newValue) {
                TagKeyAccessor.setInterner(newValue ? Interners.newWeakInterner() : Interners.newStrongInterner());
            }
            return newValue;
        }
    }

    public static class onlineModeValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setOnlineMode(newValue);
            return newValue;
        }
    }

    public static class preventProxyConnectionsValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setPreventProxyConnections(newValue);
            return newValue;
        }
    }

    public static class pvpEnabledValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setPvpEnabled(newValue);
            return newValue;
        }
    }

    public static class flightEnabledValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setFlightEnabled(newValue);
            return newValue;
        }
    }

    public static class enforceWhitelistValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) source.getServer().setEnforceWhitelist(newValue);
            return newValue;
        }
    }

    public static class reIntroduceInstantBlockUpdatesValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null) {
                for (ServerWorld world : source.getServer().getWorlds()) {
                    ((ServerWorldAccessor)world).setNeighborUpdater(newValue ?
                            new SimpleNeighborUpdater(world) : // Instant
                            new ChainRestrictedNeighborUpdater(world)
                    );
                }
            }
            return newValue;
        }
    }

    public static class blockUpdateOrderValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (newValue) {
                BlockUpdateUtils.blockUpdateDirections = (b) -> DirectionUtils.directions;
            } else {
                if (CFSettings.parityRandomBlockUpdates) {
                    BlockUpdateUtils.blockUpdateDirections = DirectionUtils::randomDirectionArray;
                } else {
                    BlockUpdateUtils.blockUpdateDirections = (b) -> UPDATE_ORDER;
                }
            }
            return newValue;
        }
    }

    public static class parityRandomBlockUpdatesValidator extends Validator<Boolean> {
        @Override public Boolean validate(ServerCommandSource source, ParsedRule<Boolean> currentRule, Boolean newValue, String string) {
            if (newValue) {
                if (!CFSettings.blockUpdateOrderFix) {
                    BlockUpdateUtils.blockUpdateDirections = DirectionUtils::randomDirectionArray;
                }
            } else if (!CFSettings.blockUpdateOrderFix) {
                BlockUpdateUtils.blockUpdateDirections = (b) -> UPDATE_ORDER;
            }
            return newValue;
        }
    }
}

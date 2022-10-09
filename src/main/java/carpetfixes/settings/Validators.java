package carpetfixes.settings;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import carpetfixes.CFSettings;
import carpetfixes.CarpetFixesServer;
import carpetfixes.helpers.BlockUpdateUtils;
import carpetfixes.helpers.DirectionUtils;
import carpetfixes.helpers.MemEfficientNeighborUpdater;
import carpetfixes.mixins.accessors.AbstractPressurePlateBlockAccessor;
import carpetfixes.mixins.accessors.WorldAccessor;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.block.ChainRestrictedNeighborUpdater;
import net.minecraft.world.block.SimpleNeighborUpdater;

import static net.minecraft.world.block.NeighborUpdater.UPDATE_ORDER;

public class Validators {


    public static class WitherGolemSpawningFixValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            ((CarvedPumpkinBlock) (Blocks.CARVED_PUMPKIN)).ironGolemPattern = null;
            ((CarvedPumpkinBlock) (Blocks.CARVED_PUMPKIN)).ironGolemDispenserPattern = null;
            return newValue;
        }
    }

    public static class WorldBorderCollisionRoundingFixValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            CFSettings.scheduleWorldBorderReset = true;
            return newValue;
        }
    }

    public static class onlineModeValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                source.getServer().setOnlineMode(newValue);
            } else {
                CarpetFixesServer.ruleScheduler.addDefaultRule(this, currentRule, newValue, string, MinecraftServer::isOnlineMode);
            }
            return newValue;
        }
    }

    public static class preventProxyConnectionsValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                source.getServer().setPreventProxyConnections(newValue);
            } else {
                CarpetFixesServer.ruleScheduler.addDefaultRule(this, currentRule, newValue, string, MinecraftServer::shouldPreventProxyConnections);
            }
            return newValue;
        }
    }

    public static class pvpEnabledValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                source.getServer().setPvpEnabled(newValue);
            } else {
                CarpetFixesServer.ruleScheduler.addDefaultRule(this, currentRule, newValue, string, MinecraftServer::isPvpEnabled);
            }
            return newValue;
        }
    }

    public static class flightEnabledValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                source.getServer().setFlightEnabled(newValue);
            } else {
                CarpetFixesServer.ruleScheduler.addDefaultRule(this, currentRule, newValue, string, MinecraftServer::isFlightEnabled);
            }
            return newValue;
        }
    }

    public static class enforceWhitelistValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                source.getServer().setEnforceWhitelist(newValue);
            } else {
                CarpetFixesServer.ruleScheduler.addDefaultRule(this, currentRule, newValue, string, MinecraftServer::isEnforceWhitelist);
            }
            return newValue;
        }
    }

    public static class reIntroduceInstantBlockUpdatesValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                for (ServerWorld world : source.getServer().getWorlds()) {
                    ((WorldAccessor) world).setNeighborUpdater(newValue ?
                            new SimpleNeighborUpdater(world) : // Instant
                            CFSettings.optimizedNeighborUpdater ?
                                    new MemEfficientNeighborUpdater(world, source.getServer().getMaxChainedNeighborUpdates()) :
                                    new ChainRestrictedNeighborUpdater(world, source.getServer().getMaxChainedNeighborUpdates())
                    );
                }
            } else {
                CarpetFixesServer.ruleScheduler.addRule(this, currentRule, newValue, string);
            }
            return newValue;
        }
    }

    public static class blockUpdateOrderValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
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
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
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

    public static class optimizedNeighborUpdaterValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (source != null && CarpetFixesServer.areWorldsLoaded) {
                if (!CFSettings.reIntroduceInstantBlockUpdates) {
                    for (ServerWorld world : source.getServer().getWorlds()) {
                        ((WorldAccessor) world).setNeighborUpdater(newValue ?
                                new MemEfficientNeighborUpdater(world, source.getServer().getMaxChainedNeighborUpdates()) :
                                new ChainRestrictedNeighborUpdater(world, source.getServer().getMaxChainedNeighborUpdates())
                        );
                    }
                }
            } else {
                CarpetFixesServer.ruleScheduler.addRule(this, currentRule, newValue, string);
            }
            return newValue;
        }
    }

    public static class wrongPressurePlateHitboxValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            AbstractPressurePlateBlockAccessor.setBox(newValue ?
                    new Box(0.075, 0.0, 0.075, 0.925, 0.25, 0.925) :
                    new Box(0.125, 0.0, 0.125, 0.875, 0.25, 0.875)
            );
            return newValue;
        }
    }

    // Changes how block updates use updateNeighbor instead of updateNeighbors
    public static class enableCustomRedstoneRuleValidator extends Validator<Boolean> {
        @Override
        public Boolean validate(ServerCommandSource source, CarpetRule<Boolean> currentRule, Boolean newValue, String string) {
            if (newValue) {
                CFSettings.useCustomRedstoneUpdates = true;
            } else {
                CFSettings.useCustomRedstoneUpdates = CFSettings.duplicateBlockUpdatesFix ||
                        CFSettings.extendedBlockUpdateOrderFix ||
                        CFSettings.redstoneComponentUpdateOrderOnBreakFix ||
                        CFSettings.uselessSelfBlockUpdateFix ||
                        CFSettings.parityRandomBlockUpdates;
            }
            return newValue;
        }
    }
}

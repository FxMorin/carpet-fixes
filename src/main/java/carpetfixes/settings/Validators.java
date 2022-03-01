package carpetfixes.settings;

import carpet.settings.ParsedRule;
import carpet.settings.Validator;
import carpetfixes.CFSettings;
import carpetfixes.mixins.accessors.TagKeyAccessor;
import com.google.common.collect.Interners;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.server.command.ServerCommandSource;

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
}

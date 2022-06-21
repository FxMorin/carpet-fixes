package carpetfixes.helpers;

import carpetfixes.CFSettings;
import carpetfixes.CarpetFixesServer;
import carpetfixes.settings.CarpetFixesMixinConfigPlugin;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.lang.reflect.Method;

import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class Utils {

    //Thanks to Fallen Breath for writing this in https://github.com/Fallen-Breath/conditional-mixin
    public static boolean isMCVersionCompat(String versionPredicate) {
        try { // fabric loader >=0.12
            return VersionPredicate.parse(versionPredicate).test(CarpetFixesMixinConfigPlugin.MINECRAFT_VERSION);
        } catch (NoClassDefFoundError e) { // fabric loader >=0.11.3 <0.12
            try {
                Class<?> clazz = Class.forName("net.fabricmc.loader.util.version.VersionPredicateParser");
                Method matches = clazz.getMethod("matches", Version.class, String.class);
                return (boolean)matches.invoke(
                        null,
                        CarpetFixesMixinConfigPlugin.MINECRAFT_VERSION,
                        versionPredicate
                );
            } catch (Exception ex) {
                CarpetFixesServer.LOGGER.error("Failed to invoke VersionPredicateParser#matches", ex);
            }
        } catch (Exception e) {
            CarpetFixesServer.LOGGER.error("Failed to parse version or version predicate {} {}: {}",
                    CarpetFixesMixinConfigPlugin.MINECRAFT_VERSION.getFriendlyString(), versionPredicate, e);
        }
        return false;
    }

    public static boolean isInModifiableLimit(World world, BlockPos pos) {
        return !world.isOutOfHeightLimit(pos) && world.getWorldBorder().contains(pos);
    }

    // Exactly like Boxes.stretch() except its block bound to prevent incorrect hitboxes being affected
    public static Box stretchBlockBound(Box box, Direction direction, double length) {
        double d = length * (double)direction.getDirection().offset();
        double e = Math.min(d, 0.0);
        double f = Math.max(d, 0.0);
        return switch (direction) {
            case WEST -> new Box(Math.ceil(box.minX + e), box.minY, box.minZ, Math.floor(box.minX + f), box.maxY, box.maxZ);
            case EAST -> new Box(Math.ceil(box.maxX + e), box.minY, box.minZ, Math.floor(box.maxX + f), box.maxY, box.maxZ);
            case DOWN -> new Box(box.minX, Math.ceil(box.minY + e), box.minZ, box.maxX, Math.floor(box.minY + f), box.maxZ);
            case UP -> new Box(box.minX, Math.ceil(box.maxY + e), box.minZ, box.maxX, Math.floor(box.maxY + f), box.maxZ);
            case NORTH -> new Box(box.minX, box.minY, Math.ceil(box.minZ + e), box.maxX, box.maxY, Math.floor(box.minZ + f));
            case SOUTH -> new Box(box.minX, box.minY, Math.ceil(box.maxZ + e), box.maxX, box.maxY, Math.floor(box.maxZ + f));
        };
    }

    public static void updateComparatorsRespectFacing(World world, BlockPos fromPos, Block block) {
        for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos pos = fromPos.offset(dir);
            if (!world.isChunkLoaded(pos)) continue;
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.COMPARATOR)) {
                if (state.get(FACING) == dir.getOpposite()) state.neighborUpdate(world, pos, block, fromPos, false);
            } else if (state.isSolidBlock(world, pos)) {
                pos = pos.offset(dir);
                state = world.getBlockState(pos);
                if (!state.isOf(Blocks.COMPARATOR)) continue;
                if (state.get(FACING) == dir.getOpposite()) state.neighborUpdate(world, pos, block, fromPos, false);
            }
        }
    }

    public static void giveShapeUpdate(World world, BlockState state, BlockPos pos, BlockPos fromPos, Direction direction) {
        BlockState oldState = world.getBlockState(pos);
        Block.replace(
                oldState,
                oldState.getStateForNeighborUpdate(direction.getOpposite(), state, world, pos, fromPos),
                world,
                pos,
                Block.NOTIFY_LISTENERS & -34,
                0
        );
    }

    public static void giveObserverUpdate(World world, BlockPos pos, Direction dir) {
        BlockPos blockPos = pos.offset(dir);
        world.getBlockState(blockPos).getStateForNeighborUpdate(dir.getOpposite(), Blocks.AIR.getDefaultState(), world, blockPos, pos);
    }

    // Just shape updates but we don't do anything with the output, when only an observer needs to be triggers.
    public static void giveObserverUpdates(World world, BlockPos pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            world.getBlockState(mutable).getStateForNeighborUpdate(direction.getOpposite(), Blocks.AIR.getDefaultState(), world, mutable, pos);
        }
    }

    // Doing vec.equals(Vec3d.ZERO) does not work due to double precision
    public static boolean isZero(Vec3d vec) {
        return vec.x == 0.0 && vec.y == 0.0 && vec.z == 0.0;
    }

    //If I was actually implementing this, the color values would have been binary in order for fast calculations.
    //Never do this in a production build, although this is better than using the RecipeManager xD
    public static DyeColor properDyeMixin(DyeColor col1, DyeColor col2) {
        if (col1.equals(col2)) return col1;
        switch(col1) {
            case WHITE -> {
                switch(col2) {
                    case BLUE -> {return DyeColor.LIGHT_BLUE;}
                    case GRAY -> {return DyeColor.LIGHT_GRAY;}
                    case BLACK -> {return DyeColor.GRAY;}
                    case GREEN -> {return DyeColor.LIME;}
                    case RED -> {return DyeColor.PINK;}
                }
            }
            case BLUE -> {
                switch(col2) {
                    case WHITE -> {return DyeColor.LIGHT_BLUE;}
                    case GREEN -> {return DyeColor.CYAN;}
                    case RED -> {return DyeColor.PURPLE;}
                }
            }
            case RED -> {
                switch(col2) {
                    case YELLOW -> {return DyeColor.ORANGE;}
                    case WHITE -> {return DyeColor.PINK;}
                    case BLUE -> {return DyeColor.PURPLE;}
                }
            }
            case GREEN -> {
                switch(col2) {
                    case BLUE -> {return DyeColor.CYAN;}
                    case WHITE -> {return DyeColor.LIME;}
                }
            }
            case YELLOW -> {if (col2.equals(DyeColor.RED)) return DyeColor.ORANGE;}
            case PURPLE -> {if (col2.equals(DyeColor.PINK)) return DyeColor.MAGENTA;}
            case PINK -> {if (col2.equals(DyeColor.PURPLE)) return DyeColor.MAGENTA;}
            case GRAY -> {if (col2.equals(DyeColor.WHITE)) return DyeColor.LIGHT_GRAY;}
            case BLACK -> {if (col2.equals(DyeColor.WHITE)) return DyeColor.GRAY;}
        }
        return null;
    }

    /**
     * Checks whether an entity should occlude signals when it drops.
     * Basically used to make wool and carpet items drop silently
     */
    public static boolean checkOccludesDrop(Entity entity){
        return !CFSettings.silentWoolDrop &&  entity instanceof ItemEntity itemEntity && itemEntity.getStack().isIn(ItemTags.DAMPENS_VIBRATIONS);
    }
}

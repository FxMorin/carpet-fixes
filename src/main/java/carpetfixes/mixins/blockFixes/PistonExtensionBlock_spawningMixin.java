package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PistonExtensionBlock;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Fixes mobs being able to spawn on moving pistons
 */

@Mixin(PistonExtensionBlock.class)
public class PistonExtensionBlock_spawningMixin extends Block {

    // TODO: Work on an api to swap block settings during runtime. Shouldn't be that hard, he says...

    public PistonExtensionBlock_spawningMixin(Settings settings) {
        super(settings.allowsSpawning((_1,_2,_3,_4) -> !CFSettings.mobsSpawnOnMovingPistonsFix));
    }
}

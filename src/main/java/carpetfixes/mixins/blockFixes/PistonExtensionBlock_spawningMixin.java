package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PistonExtensionBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PistonExtensionBlock.class)
public class PistonExtensionBlock_spawningMixin extends Block {

    public PistonExtensionBlock_spawningMixin(Settings settings) {
        super(settings.allowsSpawning((_1,_2,_3,_4) -> !CFSettings.mobsSpawnOnMovingPistonsFix));
    }
}

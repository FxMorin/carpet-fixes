package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Fixes ores broken by TNT not dropping experience
 */

@Mixin(ExperienceDroppingBlock.class)
public abstract class OreBlock_explosionMixin extends Block {

    @Shadow
    @Final
    private IntProvider experienceDropped;

    public OreBlock_explosionMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world,pos,explosion);
        if (CFSettings.oresDontDropXpWhenBlownUpFix && world instanceof ServerWorld serverWorld) {
            int i = this.experienceDropped.get(serverWorld.random);
            if (i > 0) {
                this.dropExperience(serverWorld, pos, i);
            }
        }
    }
}

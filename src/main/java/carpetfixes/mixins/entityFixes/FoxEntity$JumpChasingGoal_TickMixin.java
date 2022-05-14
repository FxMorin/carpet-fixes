package carpetfixes.mixins.entityFixes;


import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(targets = "net.minecraft.entity.passive.FoxEntity$JumpChasingGoal")
public class FoxEntity$JumpChasingGoal_TickMixin {



    /**
     * When Foxes Tick, They Check if they Are stuck on snow, But They do not respect the Powder_Snow Block
     * @return True For Blocks of Snow or Powder_Snow
     */

    @Redirect(method = "tick",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    public boolean FoxEntity_SnowCheckFix(BlockState instance, Block block) {

            return instance.getBlock().equals(Blocks.SNOW) || (CFSettings.foxesDisreguardPowderSnowFix && instance.getBlock().equals(Blocks.POWDER_SNOW));



    }






}

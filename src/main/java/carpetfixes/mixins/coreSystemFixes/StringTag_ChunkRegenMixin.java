package carpetfixes.mixins.coreSystemFixes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import carpetfixes.CarpetFixesSettings;

import java.io.DataOutput;
import net.minecraft.nbt.NbtString;

@Mixin(NbtString.class)
public abstract class StringTag_ChunkRegenMixin {

    private static final Logger LOGGER = LogManager.getLogger();

    @Mutable @Shadow @Final private String value;

    /**
     * This fix is crucial for multiple game breaking crashes, exploits, banning
     * methods. This bug happens because DataInput.writeUTF() is writing strings
     * that DataInput.readUTF() is unable to read. For some reason they do not
     * follow the same restrictions. The fix crops the strings during the write
     * so its fine during read. Therefore fixing all the issues
     */
    @Inject(method = "write(Ljava/io/DataOutput;)V", at = @At("HEAD"))
    private void respectReadLimitDuringWrite(DataOutput output, CallbackInfo ci) {
        if(CarpetFixesSettings.chunkRegenFix) {
            int strlen = this.value.length();
            if(strlen > 28501) { //Minimum number that could bypass limit
                int utflen = 0;
                for (int i = 0; i < strlen; i++) {
                    char c = this.value.charAt(i);
                    utflen += ((c >= 0x0001) && (c <= 0x007F)) ? 1 : (c <= 0x07FF) ? 2 : 3;
                    if (utflen > 65535) {
                        this.value = this.value.substring(0, i-2);
                        LOGGER.debug("Trimming Large String ("+strlen+" -> "+this.value.length()+")");
                        break;
                    }
                }
            }
        }
    }
}

package carpetfixes.mixins.coreSystemFixes;

import net.minecraft.nbt.StringTag;
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

@Mixin(StringTag.class)
public abstract class StringTag_ChunkRegenMixin {

    private static final Logger LOGGER = LogManager.getLogger();

    @Mutable @Shadow @Final private String value;

    //DataInput.readUTF() is the issue only because DataInput.writeUTF() is writing strings that
    //DataInput.readUTF() is unable to read. This crops string during the write so its fine during read

    @Inject(method = "write(Ljava/io/DataOutput;)V", at = @At("HEAD"))
    private void respectReadLimitDuringWrite(DataOutput output, CallbackInfo ci) {
        if(CarpetFixesSettings.chunkRegenFix) {
            int strlen = this.value.length();
            if(strlen > 21850) { //Minimum number that could bypass limit
                int utflen = 0;
                char c;
                /* Mostly same equation used in DataOutputStream */
                for (int i = 0; i < strlen; i++) {
                    c = this.value.charAt(i);
                    if ((c >= 0x0001) && (c <= 0x007F)) {
                        utflen++;
                    } else if (c <= 0x07FF) {
                        utflen += 2;
                    } else {
                        utflen += 3;
                    }
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

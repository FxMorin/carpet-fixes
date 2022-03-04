package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.data.validate.StructureValidatorProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static carpetfixes.CarpetFixesServer.LOGGER;

@Mixin(StructureManager.class)
public abstract class StructureManager_snbtMixin {

    @Shadow
    @Final
    private Path generatedPath;

    private Identifier id = null;

    @Shadow
    protected abstract Path getAndCheckStructurePath(Identifier id, String extension);

    @Shadow
    public abstract Structure createStructure(NbtCompound nbt);


    @Inject(
            method = "loadStructureFromFile(Lnet/minecraft/util/Identifier;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/structure/StructureManager;getAndCheckStructurePath(" +
                            "Lnet/minecraft/util/Identifier;Ljava/lang/String;)Ljava/nio/file/Path;"
            )
    )
    private void onLoadStructureFromFileReturn(Identifier id, CallbackInfoReturnable<Optional<Structure>> cir) {
        if (CFSettings.structureManagerCantLoadSnbtFix) this.id = id;
    }


    @Redirect(
            method = "loadStructureFromFile(Lnet/minecraft/util/Identifier;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;empty()Ljava/util/Optional;"
            )
    )
    private Optional<Structure> loadSnbtStructureFromFile() {
        Optional<Structure> returnValue = Optional.empty();
        if (CFSettings.structureManagerCantLoadSnbtFix && id != null && this.generatedPath.toFile().isDirectory()) {
            if (this.generatedPath.toFile().isDirectory()) {
                Path path = this.getAndCheckStructurePath(id, ".snbt");
                try {
                    returnValue = Optional.of(this.createStructure(this.toNbtCompound(path, id.getPath())));
                } catch (FileNotFoundException ignored) {
                } catch (IOException var9) {
                    LOGGER.error("Couldn't load structure from {}", path, var9);
                }
            }
        }
        return returnValue;
    }

    private NbtCompound toNbtCompound(Path path, String name) throws IOException {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String string = IOUtils.toString(bufferedReader);
            return this.write(name, NbtHelper.fromNbtProviderString(string));
        } catch (CommandSyntaxException err) {
            throw new IOException();
        }
    }

    private NbtCompound write(String key, NbtCompound compound) {
        return new StructureValidatorProvider().write(key, compound);
    }
}

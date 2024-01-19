package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.data.validate.StructureValidatorProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

/**
 * Allow the structure template manager to load snbt files if nbt versions are not found.
 * Should only be used for debugging
 */

@Mixin(StructureTemplateManager.class)
public abstract class StructureManager_snbtMixin {

    @Shadow
    @Final
    private Path generatedPath;

    @Shadow
    private static Path getAndCheckTemplatePath(Path path, Identifier id, String extension) {
        return null;
    }

    @Shadow
    public abstract StructureTemplate createTemplate(NbtCompound nbt);


    @Inject(
            method = "loadTemplateFromFile(Lnet/minecraft/util/Identifier;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/structure/StructureTemplateManager;getAndCheckTemplatePath(" +
                            "Ljava/nio/file/Path;Lnet/minecraft/util/Identifier;Ljava/lang/String;)" +
                            "Ljava/nio/file/Path;"
            )
    )
    private void cf$onLoadStructureFromFileReturn(
            Identifier id,
            CallbackInfoReturnable<Optional<StructureTemplate>> cir,
            @Share("id") LocalRef<Identifier> idRef
    ) {
        if (CFSettings.structureManagerCantLoadSnbtFix) {
            idRef.set(id);
        }
    }


    @Redirect(
            method = "loadTemplateFromFile(Lnet/minecraft/util/Identifier;)Ljava/util/Optional;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Optional;empty()Ljava/util/Optional;"
            )
    )
    private Optional<StructureTemplate> cf$loadSnbtStructureFromFile(@Share("id") LocalRef<Identifier> idRef) {
        Optional<StructureTemplate> returnValue = Optional.empty();
        if (CFSettings.structureManagerCantLoadSnbtFix) {
            Identifier id = idRef.get();
            if (id != null && this.generatedPath.toFile().isDirectory()) {
                if (this.generatedPath.toFile().isDirectory()) {
                    Path path = getAndCheckTemplatePath(this.generatedPath, id, ".snbt");
                    try {
                        returnValue = Optional.of(this.createTemplate(this.cf$toNbtCompound(path, id.getPath())));
                    } catch (FileNotFoundException ignored) {
                    } catch (IOException var9) {
                        LOGGER.error("Couldn't load structure from {}", path, var9);
                    }
                }
            }
        }
        return returnValue;
    }

    @Unique
    private NbtCompound cf$toNbtCompound(Path path, String name) throws IOException {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String string = IOUtils.toString(bufferedReader);
            return new StructureValidatorProvider().write(name, NbtHelper.fromNbtProviderString(string));
        } catch (CommandSyntaxException err) {
            throw new IOException();
        }
    }
}

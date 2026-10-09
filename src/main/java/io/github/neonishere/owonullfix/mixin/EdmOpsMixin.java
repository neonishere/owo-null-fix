package io.github.neonishere.owonullfix.mixin;

import com.mojang.serialization.DataResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * EdmOps calls input.value() in its read methods without a null check. NbtOps and JsonOps return an
 * error for null, and codecs that pass MapLike.get straight into decode rely on that.
 *
 * <p>Targets the Object bridges so owo-lib isn't needed to compile.
 */
@Pseudo
@Mixin(targets = "io.wispforest.owo.serialization.format.edm.EdmOps", remap = false)
public abstract class EdmOpsMixin {
    @Inject(
            method = {
                    "getNumberValue(Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;",
                    "getBooleanValue(Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;",
                    "getStringValue(Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;",
                    "getByteBuffer(Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;",
                    "getStream(Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;",
                    "getMapValues(Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"
            },
            at = @At("HEAD"),
            cancellable = true
    )
    private void owonullfix$failOnNull(Object input, CallbackInfoReturnable<DataResult<?>> cir) {
        if (input == null) {
            cir.setReturnValue(DataResult.error(() -> "Input is null"));
        }
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableEndFlush;

import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(SkyRendering.class)
public abstract class SkyRenderingMixin {
    @Inject(method = "drawEndLightFlash", at = @At("HEAD"), cancellable = true)
    private void noEndFlash(MatrixStack matrixStack, float f, float skyFactor, float pitch, CallbackInfo ci) {
        if (DisableToggle.DISABLE_END_FLASH.getBooleanValue()) {
            ci.cancel();
        }
    }
}

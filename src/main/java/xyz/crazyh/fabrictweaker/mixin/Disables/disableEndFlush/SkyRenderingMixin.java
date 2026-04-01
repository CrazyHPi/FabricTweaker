package xyz.crazyh.fabrictweaker.mixin.Disables.disableEndFlush;

//? if >= 1.21.9
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

//? if = 1.21
//import xyz.crazyh.fabrictweaker.FabricTweaker;

//? if >= 1.21.9
@Mixin(SkyRendering.class)
//? if = 1.21
//@Mixin(FabricTweaker.class) // dummy mixin i guess
public abstract class SkyRenderingMixin {
    //? if >= 1.21.9 {
    @Inject(method = "drawEndLightFlash", at = @At("HEAD"), cancellable = true)
    private void noEndFlash(MatrixStack matrixStack, float f, float skyFactor, float pitch, CallbackInfo ci) {
        if (DisableToggle.DISABLE_END_FLASH.getBooleanValue()) {
            ci.cancel();
        }
    }

    //? }
}

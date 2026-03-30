package xyz.crazyh.fabrictweaker.mixin.Disables.disableEndFlush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {
    //? if >= 1.21.9{
    @ModifyExpressionValue(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/EndLightFlashManager;getSkyFactor(F)F"
            )
    )
    private float noFlashSky(float original) {
        if (DisableToggle.DISABLE_END_FLASH.getBooleanValue()) {
            return 0.0F;
        }
        return original;
    }

    //? }
}

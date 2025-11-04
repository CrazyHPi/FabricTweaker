package xyz.crazyh.fabrictweaker.mixin.Disables.disableEndFlush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/EndLightFlashManager;shouldFlash()Z"
            )
    )
    private boolean noFlashSound(boolean original) {
        if (DisableToggle.DISABLE_END_FLASH.getBooleanValue()) {
            return false;
        }
        return original;
    }
}

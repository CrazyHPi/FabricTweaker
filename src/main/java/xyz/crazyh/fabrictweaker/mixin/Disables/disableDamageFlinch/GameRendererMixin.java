package xyz.crazyh.fabrictweaker.mixin.Disables.disableDamageFlinch;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    //? if 26.2
    private void cancelTile(CameraRenderState cameraRenderState, MatrixStack matrices, CallbackInfo ci) {
    //? if < 26.2
    //private void cancelTile(MatrixStack matrices, float tickProgress, CallbackInfo ci) {
        if (DisableToggle.DISABLE_DAMAGE_FLINCH.getBooleanValue()) {
            ci.cancel();
        }
    }
}

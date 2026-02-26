package xyz.crazyh.fabrictweaker.mixin.Disables.disableBossDarkenSky;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(GameRenderer.class)
public abstract class GameRenderMixin {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/BossBarHud;shouldDarkenSky()Z"
            )
    )
    private boolean noDarkenSky(boolean original) {
        if (DisableToggle.DISABLE_BOSS_DARKEN_SKY.getBooleanValue()){
            return false;
        }
        return original;
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableBossDarkenSky;

import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(GameRenderer.class)
public abstract class GameRenderMixin {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/BossBarHud;shouldDarkenSky()Z"
            )
    )
    private boolean noDarkenSky(BossBarHud instance) {
        if (DisableToggle.DISABLE_BOSS_DARKEN_SKY.getBooleanValue()){
            return false;
        }
        return instance.shouldDarkenSky();
    }
}

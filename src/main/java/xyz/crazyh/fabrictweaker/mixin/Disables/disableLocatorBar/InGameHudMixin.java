package xyz.crazyh.fabrictweaker.mixin.Disables.disableLocatorBar;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    //? if >= 1.21.6 {
    @ModifyExpressionValue(
            method = "getCurrentBarType",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;shouldShowExperienceBar()Z"
            )
    )
    private boolean shouldShowExperienceBar(boolean original) {
        return DisableToggle.DISABLE_LOCATOR_BAR.getBooleanValue() || original;
    }

    //? }
}

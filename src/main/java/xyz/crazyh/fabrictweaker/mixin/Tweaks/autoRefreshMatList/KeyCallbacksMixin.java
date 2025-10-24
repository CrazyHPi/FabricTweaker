package xyz.crazyh.fabrictweaker.mixin.Tweaks.autoRefreshMatList;

import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.utils.RandomUtils;

@Mixin(targets = "fi.dy.masa.litematica.event.KeyCallbacks$KeyCallbackHotkeys")
public abstract class KeyCallbacksMixin {
    @Inject(
            method = "onKeyAction",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;moveLayer(I)Z",
                    shift = At.Shift.AFTER
            )
    )
    private void triggerRefresh(KeyAction action, IKeybind key, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.AUTO_REFRESH_MAT_LIST.getBooleanValue()) {
            RandomUtils.refreshMaterialList();
        }
    }
}

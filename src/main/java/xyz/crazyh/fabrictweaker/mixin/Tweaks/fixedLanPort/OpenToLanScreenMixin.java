package xyz.crazyh.fabrictweaker.mixin.Tweaks.fixedLanPort;

import net.minecraft.client.gui.screen.OpenToLanScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.Configs;

@Mixin(OpenToLanScreen.class)
public abstract class OpenToLanScreenMixin {
    @Shadow
    private int port;

    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setChangedListener(Ljava/util/function/Consumer;)V",
                    ordinal = 0
            )
    )
    private void changeDefaultPort(CallbackInfo ci) {
        if (Configs.General.FIXED_LAN_PORT.getIntegerValue() != 65536) {
            this.port = Configs.General.FIXED_LAN_PORT.getIntegerValue();
        }
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableWarningLogs;

import net.minecraft.client.network.ClientConfigurationNetworkHandler;
import net.minecraft.network.packet.CustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientConfigurationNetworkHandler.class)
public abstract class ClientConfigurationNetworkHandlerMixin {

    // Unknown custom packet payload: {}
    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void unknownPayload(CustomPayload payload, CallbackInfo ci) {
        if (DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue()) {
            ci.cancel();
        }
    }
}

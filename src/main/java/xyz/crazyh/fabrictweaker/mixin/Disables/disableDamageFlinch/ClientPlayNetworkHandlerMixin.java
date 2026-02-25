package xyz.crazyh.fabrictweaker.mixin.Disables.disableDamageFlinch;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.DamageTiltS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onDamageTilt", at = @At("HEAD"), cancellable = true)
    private void cancelFlinch(DamageTiltS2CPacket packet, CallbackInfo ci) {
        if (DisableToggle.DISABLE_DAMAGE_FLINCH.getBooleanValue()) {
            ci.cancel();
        }
    }
}

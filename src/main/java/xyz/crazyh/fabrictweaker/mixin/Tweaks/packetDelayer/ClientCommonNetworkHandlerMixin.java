package xyz.crazyh.fabrictweaker.mixin.Tweaks.packetDelayer;


import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.utils.PacketUtils;

@Mixin(ClientCommonNetworkHandler.class)
public abstract class ClientCommonNetworkHandlerMixin {
    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (FeatureToggle.PACKET_DALAYER.getBooleanValue()
                && PacketUtils.PACKETS_TO_DELAY.contains(packet.getClass())
        ) {
            PacketUtils.addPacketToQueue(packet);
            ci.cancel();
        }
    }
}

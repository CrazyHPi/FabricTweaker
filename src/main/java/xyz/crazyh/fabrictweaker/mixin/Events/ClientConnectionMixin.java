package xyz.crazyh.fabrictweaker.mixin.Events;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.DisconnectionInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.FabricTweaker;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
    @Inject(method = "disconnect(Lnet/minecraft/network/DisconnectionInfo;)V", at = @At("HEAD"))
    public void onDisconnect(DisconnectionInfo disconnectionInfo, CallbackInfo ci) {
        FabricTweaker.onClientDisconnected(disconnectionInfo);
    }
}

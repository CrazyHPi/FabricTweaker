package xyz.crazyh.fabrictweaker.mixin.events;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.FabricTweaker;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void onServerLoadedWorld(CallbackInfo ci) {
        FabricTweaker.onServerLoaded((MinecraftServer) (Object) this);
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void onServerClosed(CallbackInfo ci) {
        FabricTweaker.onServerClosed((MinecraftServer) (Object) this);
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void onGameStop(boolean waitForShutdown, CallbackInfo ci) {
        FabricTweaker.onGameStop();
    }
}

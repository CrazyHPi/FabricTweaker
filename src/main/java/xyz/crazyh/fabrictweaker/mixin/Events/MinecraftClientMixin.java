package xyz.crazyh.fabrictweaker.mixin.Events;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.FabricTweaker;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void onClientTick(CallbackInfo ci) {
        FabricTweaker.onClientTick((MinecraftClient) (Object) this);
    }
}

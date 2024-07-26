package xyz.crazyh.fabrictweaker.mixin.Disables.disableSneakSlowdown;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;shouldSlowDown()Z"
            )
    )
    private boolean cancelSlowdown(ClientPlayerEntity instance) {
        if (DisableToggle.DISABLE_SNEAK_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return instance.shouldSlowDown();
    }
}

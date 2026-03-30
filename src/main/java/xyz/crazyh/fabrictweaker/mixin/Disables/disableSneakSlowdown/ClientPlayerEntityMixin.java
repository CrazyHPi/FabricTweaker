package xyz.crazyh.fabrictweaker.mixin.Disables.disableSneakSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @ModifyExpressionValue(
            //? if > 1.21
            method = "applyMovementSpeedFactors",
            //? if = 1.21
            //method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;shouldSlowDown()Z"
            )
    )
    private boolean cancelSlowdown(boolean original) {
        if (DisableToggle.DISABLE_SNEAK_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return original;
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @ModifyExpressionValue(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isBlockedFromSprinting()Z"
            )
    )
    private boolean noUseItemSlowdown(boolean original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "applyMovementSpeedFactors",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
            )
    )
    private boolean noUseItemSlowdown1(boolean original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "canStartSprinting",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isBlockedFromSprinting()Z"
            )
    )
    private boolean noUseItemSlowdown2(boolean original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "canStartSprinting",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSubmergedInWater()Z"
            )
    )
    private boolean sprintInWater(boolean original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            return false;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "canStartSprinting",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    private boolean sprintInWater1(boolean original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            return true;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "shouldStopSprinting",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    private boolean sprintInWater2(boolean original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            return true;
        }
        return original;
    }
}

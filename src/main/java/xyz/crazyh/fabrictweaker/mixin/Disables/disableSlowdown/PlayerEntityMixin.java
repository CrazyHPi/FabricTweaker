package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
            method = "getVelocityMultiplier",
            at = @At("RETURN"),
            cancellable = true
    )
    private void noSlowdown(CallbackInfoReturnable<Float> cir) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue() && cir.getReturnValue() < 1.0F) {
            cir.setReturnValue(1.0F);
        }
    }
}

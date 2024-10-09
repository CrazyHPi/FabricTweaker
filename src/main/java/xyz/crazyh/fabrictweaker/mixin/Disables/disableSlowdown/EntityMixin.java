package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @SuppressWarnings("ConstantConditions")
    @Inject(
            method = "getVelocityMultiplier",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            cancellable = true
    )
    private void noSlowdown(CallbackInfoReturnable<Float> cir) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()
                && ((Entity) (Object) this) instanceof ClientPlayerEntity
                && cir.getReturnValue() < 1.0F) {
            cir.setReturnValue(1.0F);
        }
    }
}

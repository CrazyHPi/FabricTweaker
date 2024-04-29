package xyz.crazyh.fabrictweaker.mixin.Tweaks.stepUp;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void stepUp(CallbackInfo ci) {
        if (FeatureToggle.STEP_UP.getBooleanValue()) {
            ((ClientPlayerEntity) (Object) this).setStepHeight(((ClientPlayerEntity) (Object) this).isSneaking() ? 0.6F : 1.125F);
        } else {
            ((ClientPlayerEntity) (Object) this).setStepHeight(0.6F);
        }
    }
}

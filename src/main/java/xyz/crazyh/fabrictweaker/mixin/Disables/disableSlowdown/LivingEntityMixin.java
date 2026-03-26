package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @SuppressWarnings("ConstantConditions")
    @ModifyExpressionValue(
            method = "travelInWater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D"
            )
    )
    private double noInWaterSlowdown(double original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue() && ((LivingEntity) (Object) this) instanceof ClientPlayerEntity) {
            return 1;
        }
        return original;
    }
}

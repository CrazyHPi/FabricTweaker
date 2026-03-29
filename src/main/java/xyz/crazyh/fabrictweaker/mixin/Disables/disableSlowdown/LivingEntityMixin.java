package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract float getMovementSpeed();

    @SuppressWarnings("ConstantConditions")
    @ModifyExpressionValue(
            //? if 1.21.11
            method = "travelInWater",
            //? if 1.21.10
            //method = "travelInFluid",
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

    @SuppressWarnings("ConstantConditions")
    @ModifyConstant(
            //? if 1.21.11
            method = "travelInWater",
            //? if 1.21.10
            //method = "travelInFluid",
            constant = @Constant(floatValue = 0.02f/*? if 1.21.10 >>+ ', ordinal = 1'*//*, ordinal = 1*/)
    )
    private float noInLavaSlowdown(float original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue() && ((LivingEntity) (Object) this) instanceof ClientPlayerEntity) {
            return this.getMovementSpeed();
        }
        return original;
    }
}

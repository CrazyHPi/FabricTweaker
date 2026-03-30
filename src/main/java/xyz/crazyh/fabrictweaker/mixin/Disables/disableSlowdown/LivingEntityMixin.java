package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract float getMovementSpeed();

    @SuppressWarnings("ConstantConditions")
    @ModifyExpressionValue(
            //? if 1.21.11
            method = "travelInWater",
            //? if 1.21.10
            //method = "travelInFluid",
            //? if 1.21
            //method = "travel",
            at = @At(
                    value = "INVOKE",
                    //? if > 1.21
                    target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D"
                    //? if = 1.21
                    //target = "Lnet/minecraft/block/Block;getSlipperiness()F"
            )
    )
    //? if > 1.21 {
    private double noInWaterSlowdown(double original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue() && ((LivingEntity) (Object) this) instanceof ClientPlayerEntity) {
            return 1;
        }
        return original;
    }

    //? }

    //? if = 1.21 {
    /*private float noWaterSlowdown(float original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()
                && ((LivingEntity) (Object) this) instanceof ClientPlayerEntity
                && this.isTouchingWater()
                && original > 0.6F) {
            return 0.6F;
        }
        return original;
    }

    *///? }

    @SuppressWarnings("ConstantConditions")
    @ModifyConstant(
            //? if 1.21.11
            method = "travelInWater",
            //? if 1.21.10
            //method = "travelInFluid",
            //? if 1.21
            //method = "travel",
            constant = @Constant(floatValue = 0.02f/*? if < 1.21.11 >>+ ', ordinal = 1'*//*, ordinal = 1*/)
    )
    private float noInLavaSlowdown(float original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue() && ((LivingEntity) (Object) this) instanceof ClientPlayerEntity) {
            return this.getMovementSpeed();
        }
        return original;
    }
}

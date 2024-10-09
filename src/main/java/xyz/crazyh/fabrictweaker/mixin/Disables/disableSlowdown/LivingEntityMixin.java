package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("ConstantConditions")
    @ModifyExpressionValue(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getSlipperiness()F"
            )
    )
    private float noSlowdown(float original) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()
                && ((LivingEntity) (Object) this) instanceof ClientPlayerEntity
                && this.isTouchingWater()
                && original > 0.6F) {
            return 0.6F;
        }
        return original;
    }
}

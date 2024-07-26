package xyz.crazyh.fabrictweaker.mixin.Tweaks.strictFakeSneaking;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow protected abstract boolean clipAtLedge();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(
            method = "adjustMovementForSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;clipAtLedge()Z"
            )
    )
    private boolean fakeSneaking(PlayerEntity instance) {
        if (FeatureToggle.STRICT_FAKE_SNEAKING.getBooleanValue() && ((Object) this) instanceof ClientPlayerEntity) {
            return true;
        }
        return this.clipAtLedge();
    }

    @Redirect(
            method = "adjustMovementForSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getStepHeight()F"
            )
    )
    private float nope(PlayerEntity instance) {
        if (FeatureToggle.STRICT_FAKE_SNEAKING.getBooleanValue()) {
            return 0.1F;
        }
        return instance.getStepHeight();
    }
}

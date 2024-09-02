package xyz.crazyh.fabrictweaker.mixin.Tweaks.strictFakeSneaking;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(value = PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    protected abstract boolean clipAtLedge();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //old tweakeroo compact code
//    @Redirect(
//            method = "adjustMovementForSneaking",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/player/PlayerEntity;clipAtLedge()Z"
//            )
//    )
//    private boolean fakeSneaking(PlayerEntity instance) {
//        if (FeatureToggle.STRICT_FAKE_SNEAKING.getBooleanValue() && ((Object) this) instanceof ClientPlayerEntity) {
//            return true;
//        }
//        return this.clipAtLedge();
//    }

    @ModifyExpressionValue(
            method = "adjustMovementForSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;clipAtLedge()Z"
            )
    )
    private boolean fakeSneaking(boolean original) {
        if (FeatureToggle.STRICT_FAKE_SNEAKING.getBooleanValue() && ((Object) this) instanceof ClientPlayerEntity) {
            return true;
        }
        return original;
    }

    // old compact code
    // viafabric compact
//    @ModifyVariable(
//            method = "adjustMovementForSneaking",
//            at = @At("STORE"),
//            ordinal = 0
//    )
//    private float modifyStepHeight(float original) {
//        if (FeatureToggle.STRICT_FAKE_SNEAKING.getBooleanValue()) {
//            return 0.1F;
//        }
//        return original;
//    }

    // viafabric compact
    @ModifyExpressionValue(
            method = "adjustMovementForSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getStepHeight()F"
            )
    )
    private float fakeStepHeight(float original) {
        if (FeatureToggle.STRICT_FAKE_SNEAKING.getBooleanValue()) {
            return 0.1F;
        }
        return original;
    }
}

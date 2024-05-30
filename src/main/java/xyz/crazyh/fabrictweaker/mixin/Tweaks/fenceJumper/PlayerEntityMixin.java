package xyz.crazyh.fabrictweaker.mixin.Tweaks.fenceJumper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.utils.BlockUtils;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
            method = "jump",
            at = @At("TAIL")
    )
    private void jumpHigher(CallbackInfo ci) {
        if (!FeatureToggle.FENCE_JUMPER.getBooleanValue()) {
            return;
        }
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player.input.jumping && BlockUtils.isNearFence(player)) {
            player.setVelocity(player.getVelocity().add(0.0D, 0.05D, 0.0D));
        }
    }
}

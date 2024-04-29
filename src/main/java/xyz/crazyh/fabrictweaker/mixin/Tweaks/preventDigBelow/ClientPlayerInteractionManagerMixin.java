package xyz.crazyh.fabrictweaker.mixin.Tweaks.preventDigBelow;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(
            method = "attackBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelAttack(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.PREVENT_DIG_BELOW.getBooleanValue() && pos.getY() < client.player.getY() && !client.player.isSneaking()){
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "updateBlockBreakingProgress",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelUpdate(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.PREVENT_DIG_BELOW.getBooleanValue() && pos.getY() < client.player.getY() && !client.player.isSneaking()){
            cir.setReturnValue(false);
        }
    }
}

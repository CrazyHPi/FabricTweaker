package xyz.crazyh.fabrictweaker.mixin.Tweaks.periWallHelper;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.utils.BlockUtils;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void cancelPeriWallDig(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.PERI_WALL_HELPER.getBooleanValue() && BlockUtils.isTopBlockBlacklisted(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), cancellable = true)
    private void cancelPeriWallDigProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.PERI_WALL_HELPER.getBooleanValue() && BlockUtils.isTopBlockBlacklisted(pos)) {
            cir.setReturnValue(true);
        }
    }
}

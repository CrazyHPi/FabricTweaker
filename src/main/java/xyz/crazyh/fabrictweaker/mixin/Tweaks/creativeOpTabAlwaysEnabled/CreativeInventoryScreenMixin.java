package xyz.crazyh.fabrictweaker.mixin.Tweaks.creativeOpTabAlwaysEnabled;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {
    @Inject(method = "shouldShowOperatorTab", at = @At("HEAD"), cancellable = true)
    private void alwaysOP(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.CREATIVE_OP_TAB.getBooleanValue()) {
            cir.setReturnValue(true);
        }
    }
}

package xyz.crazyh.fabrictweaker.mixin.Tweaks.preventPlaceBelow;

import fi.dy.masa.malilib.util.InfoUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.Configs;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void cancelPlace(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        BlockPos pos = hitResult.getBlockPos();
        if (FeatureToggle.PREVENT_PLACE_BELOW.getBooleanValue()
                && pos.getY() < client.player.getY() - Configs.General.PREVENT_PLACE_DEPTH.getIntegerValue()
                && !client.player.isSneaking()) {
            InfoUtils.printActionbarMessage("Place canceled");
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}

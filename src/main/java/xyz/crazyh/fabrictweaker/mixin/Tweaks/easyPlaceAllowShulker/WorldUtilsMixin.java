package xyz.crazyh.fabrictweaker.mixin.Tweaks.easyPlaceAllowShulker;

import fi.dy.masa.litematica.util.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.utils.RandomUtils;

@Mixin(WorldUtils.class)
public abstract class WorldUtilsMixin {
    @Inject(
            method = "doEasyPlaceAction",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/litematica/util/RayTraceUtils$RayTraceWrapper;getBlockHitResult()Lnet/minecraft/util/hit/BlockHitResult;"
            ),
            cancellable = true
    )
    private static void placeShulker0(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir) {
        if (FeatureToggle.EASY_PLACE_ALLOW_SHULKER.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;

            if (RandomUtils.SHULKER_BOX.contains(player.getMainHandStack().getItem())) {
                cir.setReturnValue(ActionResult.PASS); // return PASS will let vanilla handle the placement
            }
        }
    }


    @Inject(
            method = "doEasyPlaceAction",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/litematica/util/WorldUtils;placementRestrictionInEffect(Lnet/minecraft/client/MinecraftClient;)Z",
                    ordinal = 1
            ),
            cancellable = true
    )
    private static void placeShulker1(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir) {
        if (FeatureToggle.EASY_PLACE_ALLOW_SHULKER.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;
            if (RandomUtils.SHULKER_BOX.contains(player.getMainHandStack().getItem())) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}

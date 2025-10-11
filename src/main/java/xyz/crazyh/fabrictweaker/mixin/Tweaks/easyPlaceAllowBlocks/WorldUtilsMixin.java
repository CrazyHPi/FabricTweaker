package xyz.crazyh.fabrictweaker.mixin.Tweaks.easyPlaceAllowBlocks;

import fi.dy.masa.litematica.util.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.Configs;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

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
    private static void placeBlock0(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir) {
        if (FeatureToggle.EASY_PLACE_ALLOW_BLOCKS.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;

            if (Configs.Lists.EASY_PLACE_LIST.isAllowed(player.getMainHandStack().getItem())) {
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
    private static void placeBlock1(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir) {
        if (FeatureToggle.EASY_PLACE_ALLOW_BLOCKS.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;
            if (Configs.Lists.EASY_PLACE_LIST.isAllowed(player.getMainHandStack().getItem())) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}

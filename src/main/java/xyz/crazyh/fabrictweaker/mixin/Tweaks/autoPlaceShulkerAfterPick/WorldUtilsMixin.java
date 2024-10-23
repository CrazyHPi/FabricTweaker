package xyz.crazyh.fabrictweaker.mixin.Tweaks.autoPlaceShulkerAfterPick;

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
                    target = "Lfi/dy/masa/litematica/util/InventoryUtils;schematicWorldPickBlock(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/MinecraftClient;)V",
                    shift = At.Shift.AFTER
            )

    )
    private static void placeShulkerAfterPick(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir) {
        if (FeatureToggle.AUTO_PLACE_SHULKER_AFTER_PICK.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;
            if (RandomUtils.SHULKER_BOX.contains(player.getMainHandStack().getItem())) {
                mc.interactionManager.interactBlock(player, Hand.MAIN_HAND, (BlockHitResult) mc.crosshairTarget);
            }
        }
    }
}

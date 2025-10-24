package xyz.crazyh.fabrictweaker.mixin.Tweaks.temp;

import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.litematica.util.RayTraceUtils;
import fi.dy.masa.litematica.util.WorldUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
                    target = "Lfi/dy/masa/litematica/util/EntityUtils;getUsedHandForItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/util/Hand;"
            ),
            cancellable = true
    )
    private static void notPlaceBlock0(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir, @Local RayTraceUtils.RayTraceWrapper traceWrapper) {
        if (FeatureToggle.TEMP_FEATURE.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;
            BlockPos pos = traceWrapper.getBlockHitResult().getBlockPos();

            if (RandomUtils.FALLING_BLOCKS.contains(player.getMainHandStack().getItem()) &&
                    mc.world.getBlockState(pos.offset(Direction.DOWN)).getBlock() instanceof AirBlock) {
                cir.setReturnValue(ActionResult.FAIL); // return PASS will let vanilla handle the placement
            }
        }
    }
}

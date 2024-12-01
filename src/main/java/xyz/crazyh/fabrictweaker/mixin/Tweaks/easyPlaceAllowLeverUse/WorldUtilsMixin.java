package xyz.crazyh.fabrictweaker.mixin.Tweaks.easyPlaceAllowLeverUse;

import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.litematica.util.RayTraceUtils;
import fi.dy.masa.litematica.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.LeverBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(WorldUtils.class)
public abstract class WorldUtilsMixin {
    @Inject(
            method = "doEasyPlaceAction",
            at = @At(
                    value = "INVOKE",
                    target = "Lfi/dy/masa/litematica/world/SchematicWorldHandler;getSchematicWorld()Lfi/dy/masa/litematica/world/WorldSchematic;"
            ),
            cancellable = true,
            remap = false
    )
    private static void allowWhenSuccess(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir, @Local BlockPos pos) {
        if (FeatureToggle.EASY_PLACE_ALLOW_LEVER_USE.getBooleanValue()){
            ClientPlayerEntity player = mc.player;
            World world = player.getWorld();
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof LeverBlock) {
                cir.setReturnValue(ActionResult.PASS);
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
    private static void allowWhenFailed(MinecraftClient mc, CallbackInfoReturnable<ActionResult> cir, @Local RayTraceUtils.RayTraceWrapper traceWrapper) {
        if (FeatureToggle.EASY_PLACE_ALLOW_LEVER_USE.getBooleanValue()){
            BlockHitResult trace = traceWrapper.getBlockHitResult();
            BlockPos pos = trace.getBlockPos();
            ClientPlayerEntity player = mc.player;
            World world = player.getWorld();
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof LeverBlock) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}

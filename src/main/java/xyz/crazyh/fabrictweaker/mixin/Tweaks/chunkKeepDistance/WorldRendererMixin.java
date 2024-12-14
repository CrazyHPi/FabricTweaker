package xyz.crazyh.fabrictweaker.mixin.Tweaks.chunkKeepDistance;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.Configs;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @ModifyExpressionValue(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/GameRenderer;getViewDistance()F"
            )
    )
    private float keepChunks(float original) {
        if (Configs.General.CHUNK_RENDER_DISTANCE.getIntegerValue() == 0) {
            return original;
        }
        return Configs.General.CHUNK_RENDER_DISTANCE.getIntegerValue() * 16;
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableWarningLogs;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(targets = "net.minecraft.world.chunk.WorldChunk$DirectBlockEntityTickInvoker")
public abstract class WorldChunkMixin {
//    @Redirect(
//            method = "tick",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;[Ljava/lang/Object;)V"
//            )
//    )
//    private void invalidBlockEntity(Logger instance, String s, Object[] objects) {
//
//    }

    // replace @Redirect with @WrapWithCondition
    // Block entity {} @ {} state {} invalid for ticking:
    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;[Ljava/lang/Object;)V"
            )
    )
    private boolean shouldWarnInvalidBlockEntity(Logger instance, String s, Object[] objects) {
        return !DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue();
    }
}

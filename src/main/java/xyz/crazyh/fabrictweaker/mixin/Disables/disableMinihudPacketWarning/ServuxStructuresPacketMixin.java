package xyz.crazyh.fabrictweaker.mixin.Disables.disableMinihudPacketWarning;

import fi.dy.masa.minihud.network.ServuxStructuresPacket;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ServuxStructuresPacket.class)
public abstract class ServuxStructuresPacketMixin {
    @Redirect(
            method = "fromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V"
            )
    )
    private static void noInvalidWarn(Logger instance, String s) {
        if (DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue()) {
            return;
        }
        instance.warn(s);
    }

    @Redirect(
            method = "fromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V"
            )
    )
    private static void noFinalError(Logger instance, String s) {
        if (DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue()) {
            return;
        }
        instance.warn(s);
    }
}

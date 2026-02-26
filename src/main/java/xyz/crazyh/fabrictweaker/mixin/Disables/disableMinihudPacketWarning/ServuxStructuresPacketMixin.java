package xyz.crazyh.fabrictweaker.mixin.Disables.disableMinihudPacketWarning;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import fi.dy.masa.minihud.network.ServuxStructuresPacket;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ServuxStructuresPacket.class)
public abstract class ServuxStructuresPacketMixin {
    @WrapWithCondition(
            method = "fromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V",
                    remap = false
            )
    )
    private static boolean noInvalidWarn(Logger instance, String s) {
        return !DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue();
    }

    @WrapWithCondition(
            method = "fromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static boolean noError(Logger instance, String s, Object o) {
        return !DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue();
    }

    @WrapWithCondition(
            method = "fromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"
            )
    )
    private static boolean noCatchError(Logger instance, String s, Throwable throwable) {
        return !DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue();
    }

    @WrapWithCondition(
            method = "fromPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V",
                    remap = false
            )
    )
    private static boolean noFinalError(Logger instance, String s) {
        return !DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue();
    }

    @WrapWithCondition(
            method = "toPacket",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private static boolean noToPacketError(Logger instance, String s, Object o) {
        return !DisableToggle.DISABLE_MINIHUD_PACKET_WARNING.getBooleanValue();
    }
}

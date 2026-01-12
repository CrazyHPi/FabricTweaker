package xyz.crazyh.fabrictweaker.mixin.Disables.disableWarningLogs;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.CustomPayload;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

//    @Redirect(
//            method = "onScoreboardScoreUpdate",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"
//            )
//    )
//    private void unknownScoreBoard(Logger instance, String s, Object o) {
//    }

    // replace @Redirect with @WrapWithCondition
    // Received packet for unknown scoreboard: {}
    @WrapWithCondition(
            method = "onScoreboardScoreUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"
            )
    )
    private boolean shouldWarnUnknownScoreBoard(Logger instance, String s, Object o) {
        return !DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue();
    }


//    @Inject(
//            method = "onEntityPassengersSet",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void unknownEntity(EntityPassengersSetS2CPacket packet, CallbackInfo ci) {
//        Entity entity = this.world.getEntityById(packet.getEntityId());
//        if (entity == null) {
//            ci.cancel();
//        }
//    }

    // replace old trash @Inject with @WrapWithCondition
    // Received passengers for unknown entity
    @WrapWithCondition(
            method = "onEntityPassengersSet",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;)V"
            )
    )
    private boolean shouldWarnUnknownEntity(Logger instance, String s) {
        return !DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue();
    }

    // Unknown custom packet payload: {}
    @Inject(method = "warnOnUnknownPayload", at = @At("HEAD"), cancellable = true)
    private void unknownPayload(CustomPayload customPayload, CallbackInfo ci) {
        if (DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue()) {
            ci.cancel();
        }
    }

    // Ignoring player info update for unknown player {} ({})
    @WrapWithCondition(
            method = "onPlayerList",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
            )
    )
    private boolean shouldWarnUnknownPlayer(Logger instance, String s, Object o1, Object o2) {
        return !DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue();
    }
}

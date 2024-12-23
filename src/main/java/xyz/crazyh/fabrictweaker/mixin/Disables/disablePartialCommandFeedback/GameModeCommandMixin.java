package xyz.crazyh.fabrictweaker.mixin.Disables.disablePartialCommandFeedback;

import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(GameModeCommand.class)
public abstract class GameModeCommandMixin {
    @Inject(method = "sendFeedback", at = @At("HEAD"), cancellable = true)
    private static void cancelFeedback(ServerCommandSource source, ServerPlayerEntity player, GameMode gameMode, CallbackInfo ci) {
        if (DisableToggle.DISABLE_PARTIAL_COMMAND_FEEDBACK.getBooleanValue()) {
            ci.cancel();
        }
    }
}

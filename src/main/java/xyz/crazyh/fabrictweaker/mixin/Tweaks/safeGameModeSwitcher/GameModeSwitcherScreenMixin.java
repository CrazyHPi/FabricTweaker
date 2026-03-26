package xyz.crazyh.fabrictweaker.mixin.Tweaks.safeGameModeSwitcher;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameModeSwitcherScreen;
import net.minecraft.client.gui.screen.GameModeSwitcherScreen.GameModeSelection;
import net.minecraft.network.packet.c2s.play.ChangeGameModeC2SPacket;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin {

    @SuppressWarnings("DataFlowIssue")
    @Inject(
            method = "apply(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/screen/GameModeSwitcherScreen$GameModeSelection;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"
            ),
            cancellable = true
    )
    private static void onlySwitchSurvivalAndSpector(MinecraftClient client, GameModeSelection newGameMode, CallbackInfo ci) {
        if (FeatureToggle.SAFE_GAME_MODE_SWITCHER.getBooleanValue()) {
            GameMode currGameMode = client.interactionManager.getCurrentGameMode();
            client.player.networkHandler.sendPacket(new ChangeGameModeC2SPacket(
                    switch (currGameMode) {
                        case SURVIVAL -> GameMode.SPECTATOR;
                        case SPECTATOR -> GameMode.SURVIVAL;
                        case CREATIVE, ADVENTURE -> newGameMode.gameMode;
                    }
            ));

            ci.cancel();
        }
    }
}

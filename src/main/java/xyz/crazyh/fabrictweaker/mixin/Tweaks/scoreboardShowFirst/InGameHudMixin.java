package xyz.crazyh.fabrictweaker.mixin.Tweaks.scoreboardShowFirst;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

import java.util.Comparator;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @SuppressWarnings("DataFlowIssue")
    @Unique
    private static final Comparator<ScoreboardEntry> I_WILL_BE_ON_TOP = Comparator
            .comparing((ScoreboardEntry entry) -> !entry.name().getString().equalsIgnoreCase(MinecraftClient.getInstance().player.getGameProfile().name()))
            .thenComparing(Comparator.comparing(ScoreboardEntry::value).reversed())
            .thenComparing(ScoreboardEntry::owner, String.CASE_INSENSITIVE_ORDER);

    @ModifyArg(
            method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;sorted(Ljava/util/Comparator;)Ljava/util/stream/Stream;"
            )
    )
    private Comparator<ScoreboardEntry> sortEntries(Comparator<ScoreboardEntry> original) {
        return FeatureToggle.SCOREBOARD_ON_TOP.getBooleanValue() ? I_WILL_BE_ON_TOP : original;
    }
}

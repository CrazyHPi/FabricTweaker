package xyz.crazyh.fabrictweaker.mixin.Tweaks.chatWidth;

import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import xyz.crazyh.fabrictweaker.config.Configs;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    @ModifyConstant(
            method = "getWidth(D)I",
            constant = @Constant(doubleValue = 280.0D),
            require = 0
    )
    private static double width(double maxWidth) {
        if (Configs.General.MAX_CHAT_WIDTH.isModified()) {
            maxWidth = Configs.General.MAX_CHAT_WIDTH.getIntegerValue();
        }
        return maxWidth;
    }
}

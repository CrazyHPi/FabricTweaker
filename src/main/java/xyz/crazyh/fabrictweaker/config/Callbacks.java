package xyz.crazyh.fabrictweaker.config;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import net.minecraft.client.MinecraftClient;
import xyz.crazyh.fabrictweaker.gui.GuiConfigs;
import xyz.crazyh.fabrictweaker.utils.InventoryUtils;

public class Callbacks {
    public static void init(MinecraftClient mc) {
        setHotkeyCallback(Hotkeys.OPEN_GUI, GuiConfigs::openGui, true);
        setHotkeyCallback(Hotkeys.DROP_INVENTORY, InventoryUtils::dropInv, true);
    }

    public static void setHotkeyCallback(ConfigHotkey hotkey, Runnable run, boolean cancel) {
        hotkey.getKeybind().setCallback((action, key) -> {
            run.run();
            return cancel;
        });
    }
}

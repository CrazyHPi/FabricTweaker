package xyz.crazyh.fabrictweaker.config;

import fi.dy.masa.litematica.Litematica;
import fi.dy.masa.litematica.config.Configs;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.minecraft.client.MinecraftClient;
import xyz.crazyh.fabrictweaker.gui.GuiConfigs;
import xyz.crazyh.fabrictweaker.utils.InventoryUtils;
import xyz.crazyh.fabrictweaker.utils.RandomUtils;

public class Callbacks {
    public static void init(MinecraftClient mc) {
        setHotkeyCallback(Hotkeys.OPEN_GUI, GuiConfigs::openGui);
        setHotkeyCallback(Hotkeys.DROP_INVENTORY, InventoryUtils::dropInv);
        FeatureToggle.THREADED_LITEMATICA_UPDATE.setValueChangeCallback(config -> Configs.Generic.LOAD_ENTIRE_SCHEMATICS.setBooleanValue(config.getBooleanValue()));
    }

    public static void setHotkeyCallback(ConfigHotkey hotkey, Runnable run) {
        hotkey.getKeybind().setCallback((action, key) -> {
            run.run();
            return true;
        });
    }
}

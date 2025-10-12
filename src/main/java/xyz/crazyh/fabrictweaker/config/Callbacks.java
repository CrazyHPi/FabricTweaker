package xyz.crazyh.fabrictweaker.config;

import fi.dy.masa.litematica.config.Configs;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;
import xyz.crazyh.fabrictweaker.config.gui.GuiConfigs;
import xyz.crazyh.fabrictweaker.utils.InventoryUtils;
import xyz.crazyh.fabrictweaker.utils.RandomUtils;

import java.util.function.BiConsumer;

public class Callbacks {
    public static void init(MinecraftClient mc) {
        setHotkeyCallback(Hotkeys.OPEN_GUI, GuiConfigs::openGui);
        setHotkeyCallback(Hotkeys.DROP_INVENTORY, InventoryUtils::dropInv);
        setHotkeyCallback(Hotkeys.REFRESH_MAT_LIST, RandomUtils::refreshMaterialList);
        setHotkeyCallback(Hotkeys.SHARE_COORDS, RandomUtils::sendCoords);
        FeatureToggle.THREADED_LITEMATICA_UPDATE.setValueChangeCallback(config -> Configs.Generic.LOAD_ENTIRE_SCHEMATICS.setBooleanValue(config.getBooleanValue()));
    }

    public static void setHotkeyCallback(ConfigHotkey hotkey, Runnable run) {
        hotkey.getKeybind().setCallback((action, key) -> {
            run.run();
            return true;
        });
    }

    public static void setHotkeyCallback(ConfigHotkey hotkey, BiConsumer<KeyAction, IKeybind> run) {
        hotkey.getKeybind().setCallback((action, key) -> {
            run.accept(action, key);
            return true;
        });
    }
}

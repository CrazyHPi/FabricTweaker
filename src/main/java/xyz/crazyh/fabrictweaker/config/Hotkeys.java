package xyz.crazyh.fabrictweaker.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class Hotkeys {
    //todo list
    // drop inv

    public static final ConfigHotkey OPEN_GUI = new ConfigHotkey("Open Gui", "END", "Press to Open Gui");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            OPEN_GUI
    );
}

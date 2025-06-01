package xyz.crazyh.fabrictweaker.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class Hotkeys {
    public static final ConfigHotkey DROP_INVENTORY = new ConfigHotkey("Drop Inventory", "", "Press to drop inventory items according to black/white list");
    public static final ConfigHotkey OPEN_GUI = new ConfigHotkey("Open Gui", "END", "Press to Open Gui");
    public static final ConfigHotkey SHARE_COORDS = new ConfigHotkey("Share Coords to Chat", "", "Press to send current coords to chat in VoxelMap format");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            DROP_INVENTORY,
            OPEN_GUI,
            SHARE_COORDS
    );
}

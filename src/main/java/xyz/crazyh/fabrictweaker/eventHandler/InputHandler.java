package xyz.crazyh.fabrictweaker.eventHandler;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.hotkeys.*;
import xyz.crazyh.fabrictweaker.Reference;
import xyz.crazyh.fabrictweaker.config.DisableToggle;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.config.Hotkeys;

public class InputHandler implements IKeybindProvider, IMouseInputHandler, IKeyboardInputHandler {
    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
        super();
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (FeatureToggle f : FeatureToggle.values()){
            manager.addKeybindToMap(f.getKeybind());
        }
        for (DisableToggle d : DisableToggle.values()) {
            manager.addKeybindToMap(d.getKeybind());
        }
        for (IHotkey h : Hotkeys.HOTKEY_LIST) {
            manager.addKeybindToMap(h.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Reference.MOD_NAME, "fabrictweaker.hotkeys.category.feature_toggle_hotkeys", ImmutableList.copyOf(FeatureToggle.values()));
        manager.addHotkeysForCategory(Reference.MOD_NAME, "fabrictweaker.hotkeys.category.disable_toggle_hotkeys", ImmutableList.copyOf(FeatureToggle.values()));
        manager.addHotkeysForCategory(Reference.MOD_NAME, "fabrictweaker.hotkeys.category", Hotkeys.HOTKEY_LIST);
    }
}

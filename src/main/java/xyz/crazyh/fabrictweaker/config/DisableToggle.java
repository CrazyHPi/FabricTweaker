package xyz.crazyh.fabrictweaker.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;

public enum DisableToggle implements IHotkeyTogglable, IConfigNotifiable<IConfigBoolean> {
    ;
    public static final ImmutableList<DisableToggle> VALUES = ImmutableList.copyOf(values());

    private final String name;
    private final String comment;
    private final String prettyName;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final boolean singlePlayer;
    private boolean valueBoolean;
    private IValueChangeCallback<IConfigBoolean> callback;

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName)
    {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.singlePlayer = singlePlayer;
        this.comment = comment;
        this.prettyName = prettyName;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }

    @Override
    public boolean getBooleanValue() {
        return false;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return false;
    }

    @Override
    public void setBooleanValue(boolean value) {

    }

    @Override
    public void onValueChanged() {

    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<IConfigBoolean> callback) {

    }

    @Override
    public IKeybind getKeybind() {
        return null;
    }

    @Override
    public ConfigType getType() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getComment() {
        return "";
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {

    }

    @Override
    public JsonElement getAsJsonElement() {
        return null;
    }
}

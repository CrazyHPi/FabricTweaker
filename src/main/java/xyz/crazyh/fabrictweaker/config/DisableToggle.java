package xyz.crazyh.fabrictweaker.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.StringUtils;
import xyz.crazyh.fabrictweaker.FabricTweaker;
import xyz.crazyh.fabrictweaker.Reference;

public enum DisableToggle implements IHotkeyTogglable, IConfigNotifiable<IConfigBoolean> {
    //A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    //todo
    // disable explosion sound when wither near by
    DISABLE_BELL_SOUND("Disable Bell Sound", false, "Disable bell sound"),
    DISABLE_BLOCK_BREAKING_COOLDOWN("Disable Block Breaking Cooldown", false, "Remove 5gt delay between breaking two non-instant mined block"),
    DISABLE_BOSS_DARKEN_SKY("Disable Boss Darken Sky", false, "Disable darken sky when boss(wither) near by"),
    DISABLE_EXPLOSION_SOUND("Disable Explosion Sound", false, "Disable explosion sounds caused by tnt, creeper, wither skull"),
    DISABLE_LEVITATION("Disable Levitation", false, "Disable LEVITATION effect, basically cheating"),
    DISABLE_LOCATOR_BAR("Disable Locator Bar", false, "Always display experience bar"),
    DISABLE_PLAYER_SLOWDOWN("Disable Player Slowdown", false, "Disable player related slowdown"),
    DISABLE_PARTIAL_COMMAND_FEEDBACK("Disable Partial Command Feedback", false, "Disable SOME command feedback, only works in single player"),
    DISABLE_SNEAK_SLOWDOWN("Disable Sneak Slowdown", false, "Disable slowdown effect when sneaking."),
    DISABLE_WITHER_SOUND("Disable Wither Sound", false, "Disable wither ambient and hurt sound, not including skull explode sound"),
    // this should be temp rule
    DISABLE_MINIHUD_PACKET_WARNING("Disable MiniHud Warning", false, "what is this???"),
    ;
    public static final ImmutableList<DisableToggle> VALUES = ImmutableList.copyOf(values());

    private final static String FEATURE_KEY = Reference.MOD_ID + ".config.feature_toggle";

    private final String name;
    private String comment;
    private String prettyName;
    private String translatedName;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final boolean singlePlayer;
    private boolean valueBoolean;
    private IValueChangeCallback<IConfigBoolean> callback;
    private boolean dirty = false;

    // FabricTweaker's main toggle constructor
    DisableToggle(String name, boolean defaultValue, String comment) {
        this(name, defaultValue, false, "", KeybindSettings.DEFAULT, comment,
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings) {
        this(name, defaultValue, false, defaultHotkey, settings,
                buildTranslateName(name, "comment"),
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT,
                buildTranslateName(name, "comment"),
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName, String translatedName) {
        this(name, defaultValue, false, defaultHotkey,
                comment,
                prettyName,
                translatedName);
    }

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment, String prettyName, String translatedName) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT,
                comment,
                prettyName,
                translatedName);
    }

    // Backwards Compatible constructors - START
    DisableToggle(String name, boolean defaultValue, String defaultHotkey, String comment) {
        this(name, defaultValue, false, defaultHotkey, KeybindSettings.DEFAULT,
                comment,
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT,
                comment,
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings, String comment) {
        this(name, defaultValue, false, defaultHotkey, settings,
                comment,
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment) {
        this(name, defaultValue, singlePlayer, defaultHotkey, settings,
                comment,
                buildTranslateName(name, "prettyName"),
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName) {
        this(name, defaultValue, false, defaultHotkey,
                comment,
                prettyName,
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment, String prettyName) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT,
                comment,
                prettyName,
                buildTranslateName(name, "name"));
    }

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        this(name, defaultValue, singlePlayer, defaultHotkey, settings,
                comment,
                prettyName,
                buildTranslateName(name, "name"));
    }
    // Backwards Compatible constructors - END

    DisableToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName, String translatedName) {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.singlePlayer = singlePlayer;
        this.comment = comment;
        this.prettyName = prettyName;
        this.translatedName = translatedName;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }


    @Override
    public boolean getBooleanValue() {
        return this.valueBoolean;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultValueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value) {
        boolean oldValue = this.valueBoolean;
        this.valueBoolean = value;

        if (oldValue != this.valueBoolean) {
            this.onValueChanged();
        }
    }

    @Override
    public void onValueChanged() {
        if (this.callback != null) {
            this.callback.onValueChanged(this);
        }
    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<IConfigBoolean> callback) {
        this.callback = callback;
    }

    @Override
    public IKeybind getKeybind() {
        return this.keybind;
    }

    @Override
    public ConfigType getType() {
        return ConfigType.HOTKEY;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getComment() {
        String comment = StringUtils.getTranslatedOrFallback("config.comment." + this.getName().toLowerCase(), this.comment);

        if (comment != null && this.singlePlayer) {
            return comment + "\n" + StringUtils.translate("tweakeroo.label.config_comment.single_player_only");
        }

        return comment;
    }

    @Override
    public String getTranslatedName() {
        String name = StringUtils.getTranslatedOrFallback(this.translatedName, this.name);

        if (this.singlePlayer) {
            name = GuiBase.TXT_GOLD + name + GuiBase.TXT_RST;
        }

        return name;
    }

    @Override
    public void setPrettyName(String s) {
        this.prettyName = s;
    }

    @Override
    public void setTranslatedName(String s) {
        this.translatedName = s;
    }

    @Override
    public void setComment(String s) {
        this.comment = s;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void markDirty() {
        this.dirty = true;
    }

    @Override
    public void markClean() {
        this.dirty = false;
    }

    @Override
    public void checkIfClean() {
        if (this.isDirty()) {
            this.markClean();
            this.onValueChanged();
        }
    }

    // translation stuff, will not be implemented, nobody gives a fuck
    private static String buildTranslateName(String name, String type) {
        return FEATURE_KEY + "." + type + "." + name;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            if (element.isJsonPrimitive()) {
                this.valueBoolean = element.getAsBoolean();
            } else {
                FabricTweaker.LOGGER.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        } catch (Exception e) {
            FabricTweaker.LOGGER.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }

    @Override
    public JsonElement getAsJsonElement() {
        return new JsonPrimitive(this.valueBoolean);
    }
}

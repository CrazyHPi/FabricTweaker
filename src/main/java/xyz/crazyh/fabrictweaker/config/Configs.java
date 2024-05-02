package xyz.crazyh.fabrictweaker.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import xyz.crazyh.fabrictweaker.Reference;
import xyz.crazyh.fabrictweaker.utils.InventoryUtils;

import java.io.File;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static class General {
        public static final ConfigBoolean PLACE_HOLDER = new ConfigBoolean("LOL NO Tweaks HERE", false, "Not a comment");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                PLACE_HOLDER
        );
    }

    public static class Lists {
        public static final ConfigOptionList DROP_INV_LIST_TYPE = new ConfigOptionList("Drop Inventory List Type", UsageRestriction.ListType.WHITELIST, "The list type of dropping inventory");
        public static final ConfigStringList DROP_INV_BLACKLIST = new ConfigStringList("Drop Inventory Blacklist", ImmutableList.of("minecraft:diamond_pickaxe"), "Items that will not allowed to be dropped");
        public static final ConfigStringList DROP_INV_WHITELIST = new ConfigStringList("Drop Inventory Whitelist", ImmutableList.of("minecraft:stone", "minecraft:deepslate"), "Items that will be dropped");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                DROP_INV_LIST_TYPE,
                DROP_INV_BLACKLIST,
                DROP_INV_WHITELIST
        );
    }

    public static void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);
        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "General", General.OPTIONS);
                ConfigUtils.readConfigBase(root, "Lists", Lists.OPTIONS);
                ConfigUtils.readConfigBase(root, "HotKeys", Hotkeys.HOTKEY_LIST);
                ConfigUtils.readHotkeyToggleOptions(root, "TweakHotkeys", "Tweaks", FeatureToggle.VALUES);
                ConfigUtils.readHotkeyToggleOptions(root, "DisableHotkeys", "Disables", DisableToggle.VALUES);
            }
        }

        InventoryUtils.ITEM_DROP_LIST.setListType((UsageRestriction.ListType) Lists.DROP_INV_LIST_TYPE.getOptionListValue());
        InventoryUtils.ITEM_DROP_LIST.setListContents(
                Lists.DROP_INV_BLACKLIST.getStrings(),
                Lists.DROP_INV_WHITELIST.getStrings()
        );
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();
        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "General", General.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Lists", Lists.OPTIONS);
            ConfigUtils.writeConfigBase(root, "HotKeys", Hotkeys.HOTKEY_LIST);
            ConfigUtils.writeHotkeyToggleOptions(root, "TweakHotkeys", "Tweaks", FeatureToggle.VALUES);
            ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkeys", "Disables", DisableToggle.VALUES);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}

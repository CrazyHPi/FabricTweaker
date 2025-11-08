package xyz.crazyh.fabrictweaker.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import xyz.crazyh.fabrictweaker.Reference;
import xyz.crazyh.fabrictweaker.utils.InventoryUtils;
import xyz.crazyh.fabrictweaker.utils.RandomUtils;
import xyz.crazyh.fabrictweaker.utils.WCItemRestriction;

import java.nio.file.Files;
import java.nio.file.Path;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static class General {
        public static final ConfigInteger ADDITIONAL_COOLDOWN_VALUE = new ConfigInteger("Additional Cooldown Value", 3, 1, 20, "The time in gametick added to block breaking cooldown");
        public static final ConfigBoolean AUTO_REFRESH_INV = new ConfigBoolean("Auto Refresh Inventory", false, "Refresh inventory in a certain time interval.");
        public static final ConfigInteger REFRESH_INV_INTERVAL = new ConfigInteger("Refresh Inventory Interval", 20, 1, 6000, "The time interval in gametick to resync inventory");
        public static final ConfigInteger CHUNK_RENDER_DISTANCE = new ConfigInteger("Chunk Render Distance", 0, 0, 512, "The chunk render distance that are kept to render. Default=0 will use vanilla setting.");
        public static final ConfigInteger FIXED_LAN_PORT = new ConfigInteger("Fixed Lan Port", 25565, 1024, 65536, false, "The default port used by \"Open to Lan\". Use 65536 to disable");
        public static final ConfigInteger MAX_CHAT_WIDTH = new ConfigInteger("Max Chat Width", 280, 280, 1000, "Width of chat ofc");
        public static final ConfigInteger PREVENT_DIG_DEPTH = new ConfigInteger("Prevent Dig Depth", 0, 0, 16, "The amount of blocks allowed by tweak PreventDigBelow, default=0 won't allow any block below you get dug");
        public static final ConfigInteger PREVENT_PLACE_DEPTH = new ConfigInteger("Prevent Place Depth", 0, 0, 16, "The amount of blocks allowed by tweak PreventPlaceBelow, default=0 won't allow any block below you get placed");
        public static final ConfigBoolean SEND_COORDS_TO_PUBLIC_CHAT = new ConfigBoolean("Send Coords to Public Chat", false, "Whether to send the coords to the public chat. If False, it will only display it in client chat hud.");
        public static final ConfigDouble SNEAK_HEIGHT = new ConfigDouble("Strict Fake Sneak Height", 0.001, 0.0001, 10.0, "The tolerance fall height allowed by Strict Fake Sneaking");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                ADDITIONAL_COOLDOWN_VALUE,
                AUTO_REFRESH_INV,
                REFRESH_INV_INTERVAL,
                CHUNK_RENDER_DISTANCE,
                FIXED_LAN_PORT,
                MAX_CHAT_WIDTH,
                PREVENT_DIG_DEPTH,
                PREVENT_PLACE_DEPTH,
                SEND_COORDS_TO_PUBLIC_CHAT,
                SNEAK_HEIGHT
        );
    }

    public static class Lists {
        public static final ConfigStringList DISABLE_SOUND_LIST = new ConfigStringList("Disable Sound List", ImmutableList.of(), "List of sound event identifier to be disabled, e.g. \"block.dispenser.dispense\"");
        public static final ConfigOptionList DROP_INV_LIST_TYPE = new ConfigOptionList("Drop Inventory List Type", UsageRestriction.ListType.WHITELIST, "The list type of dropping inventory");
        public static final ConfigStringList DROP_INV_BLACKLIST = new ConfigStringList("Drop Inventory Blacklist", ImmutableList.of("minecraft:diamond_pickaxe"), "Items that will not allowed to be dropped, wildcard \"*\" is supported, e.g. *_ore");
        public static final ConfigStringList DROP_INV_WHITELIST = new ConfigStringList("Drop Inventory Whitelist", ImmutableList.of("minecraft:stone", "minecraft:deepslate"), "Items that will be dropped, wildcard \"*\" is supported, e.g. *_ore");

        public static final ConfigStringList EASY_PLACE_ALLOW_BLOCKS_LIST = new ConfigStringList("Easy Place Allow Blocks List", ImmutableList.of(), "Blocks that will be allowed when easy place is enabled. Wildcard \"*\" is supported.");
        public static final WCItemRestriction EASY_PLACE_LIST_RESTRICTION = new WCItemRestriction();

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                DISABLE_SOUND_LIST,
                DROP_INV_LIST_TYPE,
                DROP_INV_BLACKLIST,
                DROP_INV_WHITELIST,
                EASY_PLACE_ALLOW_BLOCKS_LIST
        );
    }

    public static void loadFromFile() {
        Path configFile = FileUtils.getConfigDirectoryAsPath().resolve(CONFIG_FILE_NAME);
        if (Files.exists(configFile) && Files.isReadable(configFile)) {
            JsonElement element = JsonUtils.parseJsonFileAsPath(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "General", General.OPTIONS);
                ConfigUtils.readConfigBase(root, "Lists", Lists.OPTIONS);
                ConfigUtils.readConfigBase(root, "HotKeys", Hotkeys.HOTKEY_LIST);
                ConfigUtils.readHotkeyToggleOptions(root, "TweakHotkeys", "Tweaks", FeatureToggle.VALUES);
                ConfigUtils.readHotkeyToggleOptions(root, "DisableHotkeys", "Disables", DisableToggle.VALUES);
            }
        }
    }

    public static void updateListsRestrictions() {
        InventoryUtils.ITEM_DROP_LIST.setListType((UsageRestriction.ListType) Lists.DROP_INV_LIST_TYPE.getOptionListValue());
        InventoryUtils.ITEM_DROP_LIST.setListContents(
                Lists.DROP_INV_BLACKLIST.getStrings(),
                Lists.DROP_INV_WHITELIST.getStrings()
        );

        Lists.EASY_PLACE_LIST_RESTRICTION.setListType(UsageRestriction.ListType.WHITELIST);
        Lists.EASY_PLACE_LIST_RESTRICTION.setListContents(
                ImmutableList.of(),
                Lists.EASY_PLACE_ALLOW_BLOCKS_LIST.getStrings()
        );

        RandomUtils.updateDisabledSound(Lists.DISABLE_SOUND_LIST.getStrings());
    }

    public static void saveToFile() {
        Path dir = FileUtils.getConfigDirectoryAsPath();
        if (!Files.exists(dir)) {
            FileUtils.createDirectoriesIfMissing(dir);
        }

        if (Files.isDirectory(dir)) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "General", General.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Lists", Lists.OPTIONS);
            ConfigUtils.writeConfigBase(root, "HotKeys", Hotkeys.HOTKEY_LIST);
            ConfigUtils.writeHotkeyToggleOptions(root, "TweakHotkeys", "Tweaks", FeatureToggle.VALUES);
            ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkeys", "Disables", DisableToggle.VALUES);

            JsonUtils.writeJsonToFileAsPath(root, dir.resolve(CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
        updateListsRestrictions();
    }

    @Override
    public void save() {
        saveToFile();
    }
}

package xyz.crazyh.fabrictweaker.gui;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.BooleanHotkeyGuiWrapper;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.screen.Screen;
import xyz.crazyh.fabrictweaker.Reference;
import xyz.crazyh.fabrictweaker.config.Configs;
import xyz.crazyh.fabrictweaker.config.DisableToggle;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;
import xyz.crazyh.fabrictweaker.config.Hotkeys;

import java.util.Collections;
import java.util.List;

public class GuiConfigs extends GuiConfigsBase {
    public static ImmutableList<FeatureToggle> TWEAK_LIST = FeatureToggle.VALUES;
    private static ImmutableList<DisableToggle> DISABLE_LIST = DisableToggle.VALUES;

    private static ConfigGuiTab tab = ConfigGuiTab.TWEAKS;

    public GuiConfigs() {
        super(10, 50, Reference.MOD_ID, null, "fabrictweaker.gui.title.configs", String.format("%s", Reference.MOD_VERSION));
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values()) {
            x += this.createButton(x, y, -1, tab);
        }
    }

    @Override
    protected int getConfigWidth() {
        ConfigGuiTab tab = GuiConfigs.tab;

        if (tab == ConfigGuiTab.GENERIC) {
            return 120;
        } else if (tab == ConfigGuiTab.LISTS) {
            return 200;
        }

        return 260;
    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(GuiConfigs.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }

    public static void openGui() {
        GuiBase.openGui(new GuiConfigs());
    }

    private static class ButtonListener implements IButtonActionListener {
        private final GuiConfigs parent;
        private final ConfigGuiTab tab;

        public ButtonListener(ConfigGuiTab tab, GuiConfigs parent) {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            GuiConfigs.tab = this.tab;
            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = GuiConfigs.tab;

        if (tab == ConfigGuiTab.GENERIC) {
            configs = Configs.General.OPTIONS;
        } else if (tab == ConfigGuiTab.LISTS) {
            configs = Configs.Lists.OPTIONS;
        } else if (tab == ConfigGuiTab.HOTKEYS) {
            configs = Hotkeys.HOTKEY_LIST;
        } else if (tab == ConfigGuiTab.TWEAKS) {
            return ConfigOptionWrapper.createFor(TWEAK_LIST.stream().map(this::wrapConfig).toList());
        } else if (tab == ConfigGuiTab.DISABLES) {
            return ConfigOptionWrapper.createFor(DISABLE_LIST.stream().map(this::wrapConfig).toList());
        } else {
            return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    protected BooleanHotkeyGuiWrapper wrapConfig(IHotkeyTogglable config) {
        return new BooleanHotkeyGuiWrapper(config.getName(), config, config.getKeybind());
    }

    @Override
    protected boolean useKeybindSearch() {
        return GuiConfigs.tab == ConfigGuiTab.TWEAKS ||
                GuiConfigs.tab == ConfigGuiTab.HOTKEYS ||
                GuiConfigs.tab == ConfigGuiTab.DISABLES;
    }

    public enum ConfigGuiTab {
        GENERIC("fabrictweaker.gui.button.config_gui.generic"),
        LISTS("fabrictweaker.gui.button.config_gui.lists"),
        TWEAKS("fabrictweaker.gui.button.config_gui.tweaks"),
        DISABLES("fabrictweaker.gui.button.config_gui.disables"),
        HOTKEYS("fabrictweaker.gui.button.config_gui.hotkeys");

        private final String translationKey;

        ConfigGuiTab(String translationKey) {
            this.translationKey = translationKey;
        }

        public String getDisplayName() {
            return StringUtils.translate(this.translationKey);
        }
    }


}

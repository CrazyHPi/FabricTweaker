package xyz.crazyh.fabrictweaker.utils;

import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.materials.MaterialListBase;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.util.InfoUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.crazyh.fabrictweaker.config.Configs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomUtils {
    public static final Set<Item> SHULKER_BOX = Set.of(
            Items.SHULKER_BOX,
            Items.WHITE_SHULKER_BOX,
            Items.ORANGE_SHULKER_BOX,
            Items.MAGENTA_SHULKER_BOX,
            Items.LIGHT_BLUE_SHULKER_BOX,
            Items.YELLOW_SHULKER_BOX,
            Items.LIME_SHULKER_BOX,
            Items.PINK_SHULKER_BOX,
            Items.GRAY_SHULKER_BOX,
            Items.LIGHT_GRAY_SHULKER_BOX,
            Items.CYAN_SHULKER_BOX,
            Items.PURPLE_SHULKER_BOX,
            Items.BLUE_SHULKER_BOX,
            Items.BROWN_SHULKER_BOX,
            Items.GREEN_SHULKER_BOX,
            Items.RED_SHULKER_BOX,
            Items.BLACK_SHULKER_BOX
    );

    public static final Set<Item> FALLING_BLOCKS = Set.of(
            // all concrete powder + sand + gravel
            Items.WHITE_CONCRETE_POWDER,
            Items.ORANGE_CONCRETE_POWDER,
            Items.MAGENTA_CONCRETE_POWDER,
            Items.LIGHT_BLUE_CONCRETE_POWDER,
            Items.YELLOW_CONCRETE_POWDER,
            Items.LIME_CONCRETE_POWDER,
            Items.PINK_CONCRETE_POWDER,
            Items.GRAY_CONCRETE_POWDER,
            Items.LIGHT_GRAY_CONCRETE_POWDER,
            Items.CYAN_CONCRETE_POWDER,
            Items.PURPLE_CONCRETE_POWDER,
            Items.BLUE_CONCRETE_POWDER,
            Items.BROWN_CONCRETE_POWDER,
            Items.GREEN_CONCRETE_POWDER,
            Items.RED_CONCRETE_POWDER,
            Items.BLACK_CONCRETE_POWDER,
            Items.SAND,
            Items.GRAVEL
    );

    public static void sendCoords() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ClientPlayNetworkHandler networkHandler = player.networkHandler;
        String coords = String.format("[x:%d, y:%d, z:%d]", (int) player.getX(), (int) player.getY(), (int) player.getZ());

        if (Configs.General.SEND_COORDS_TO_PUBLIC_CHAT.getBooleanValue()) {
            networkHandler.sendChatMessage(coords);
        } else {
            player.sendMessage(Text.literal(coords), false);
        }
    }

    public static void refreshMaterialList() {
        // basically copy-paste from /fi/dy/masa/litematica/event/KeyCallbacks.java:360
        MaterialListBase materialList = DataManager.getMaterialList();

        if (materialList == null) {
            SchematicPlacement schematicPlacement = DataManager.getSchematicPlacementManager().getSelectedSchematicPlacement();

            if (schematicPlacement != null) {
                materialList = schematicPlacement.getMaterialList();
                materialList.reCreateMaterialList();
            } else {
                InfoUtils.showGuiOrInGameMessage(Message.MessageType.ERROR, "litematica.message.error.no_placement_selected");
            }
        }

        if (materialList != null) {
            materialList.reCreateMaterialList();
        }

    }

    public static SoundEvent getSoundEventFromStr(String sound) {
        return Registries.SOUND_EVENT.get(Identifier.of(sound));
    }

    private static final Set<SoundEvent> DISABLED_SOUND = new HashSet<>();

    public static boolean shouldMuteSound(SoundEvent sound) {
        return DISABLED_SOUND.contains(sound);
    }

    public static void updateDisabledSound(List<String> soundList) {
        DISABLED_SOUND.clear();
        for (String sound : soundList) {
            SoundEvent soundEvent = getSoundEventFromStr(sound);
            if (soundEvent != null) {
                DISABLED_SOUND.add(soundEvent);
            }
        }
    }
}

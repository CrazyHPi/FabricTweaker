package xyz.crazyh.fabrictweaker.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

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

    public static void sendCoords() {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;



        client.player.sendMessage(Text.literal(
                String.format("[x:%d, y:%d, z:%d]",
                        (int) player.getX(),
                        (int) player.getY(),
                        (int) player.getZ())
        ), false);
    }

}

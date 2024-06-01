package xyz.crazyh.fabrictweaker.utils;

import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.util.InfoUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    public static final WCItemRestriction ITEM_DROP_LIST = new WCItemRestriction();

    public static void dropInv() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        ClientPlayerInteractionManager interactionManager = mc.interactionManager;

        if (mc.currentScreen instanceof InventoryScreen) {
            return;
        }
        if (player == null || interactionManager == null) {
            return;
        }

        DefaultedList<ItemStack> mainInv = player.getInventory().main;
        if (mainInv.isEmpty()) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (!mainInv.get(i).isEmpty() && ITEM_DROP_LIST.isAllowed(mainInv.get(i).getItem())){
                interactionManager.clickSlot(0, i + 36, 1, SlotActionType.THROW, player);
            }
        }

        for (int i = 9; i < 36; i++){
            if (!mainInv.get(i).isEmpty() && ITEM_DROP_LIST.isAllowed(mainInv.get(i).getItem())){
                interactionManager.clickSlot(0, i, 1, SlotActionType.THROW, player);
            }
        }

        InfoUtils.showGuiOrActionBarMessage(Message.MessageType.INFO, "fabrictweaker.message.dropinv");
    }

    // might be useful, who knows
    public static List<Item> getItemsFromNames(List<String> strings) {
        List<Item> result = new ArrayList<>();
        for (String s : strings){
            result.add(getItemFromName(s));
        }
        return result;
    }

    public static Item getItemFromName(String s) {
        try {
            return Registries.ITEM.get(new Identifier(s));
        } catch (Exception e) {
            return null;
        }
    }
}

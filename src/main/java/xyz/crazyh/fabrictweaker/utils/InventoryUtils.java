package xyz.crazyh.fabrictweaker.utils;

import fi.dy.masa.malilib.util.restrictions.ItemRestriction;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
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
import xyz.crazyh.fabrictweaker.config.Configs;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    /*private static final List<Item> itemDropList = new ArrayList<>();

    public static void updateItemDropList(List<Item> listIn){
        itemDropList.clear();
        itemDropList.addAll(listIn);
    }*/

    public static final ItemRestriction ITEM_DROP_LIST = new ItemRestriction();

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

        DefaultedList<ItemStack> inv = player.getInventory().main;
        if (inv.isEmpty()) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (!inv.get(i).isEmpty() && ITEM_DROP_LIST.isAllowed(inv.get(i).getItem())){
                interactionManager.clickSlot(0, i + 36, 1, SlotActionType.THROW, player);
            }
        }

        for (int i = 9; i < 36; i++){
            if (!inv.get(i).isEmpty() && ITEM_DROP_LIST.isAllowed(inv.get(i).getItem())){
                interactionManager.clickSlot(0, i, 1, SlotActionType.THROW, player);
            }
        }
    }

    private static boolean dropOrNot(Item item) {
        if (Configs.Lists.DROP_INV_LIST_TYPE.getOptionListValue() == UsageRestriction.ListType.NONE) {
            return false;
        }
        if (Configs.Lists.DROP_INV_LIST_TYPE.getOptionListValue() == UsageRestriction.ListType.BLACKLIST) {

        }

        return true;
    }

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

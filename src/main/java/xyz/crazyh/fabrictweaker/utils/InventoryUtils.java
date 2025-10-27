package xyz.crazyh.fabrictweaker.utils;

import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.util.InfoUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    public static final WCItemRestriction ITEM_DROP_LIST = new WCItemRestriction();

    // auto drop inventory
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

        DefaultedList<ItemStack> mainInv = player.getInventory().getMainStacks();
        if (mainInv.isEmpty()) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (!mainInv.get(i).isEmpty() && ITEM_DROP_LIST.isAllowed(mainInv.get(i).getItem())) {
                interactionManager.clickSlot(0, i + 36, 1, SlotActionType.THROW, player);
            }
        }

        for (int i = 9; i < 36; i++) {
            if (!mainInv.get(i).isEmpty() && ITEM_DROP_LIST.isAllowed(mainInv.get(i).getItem())) {
                interactionManager.clickSlot(0, i, 1, SlotActionType.THROW, player);
            }
        }

        InfoUtils.showGuiOrActionBarMessage(Message.MessageType.INFO, "fabrictweaker.message.dropinv");
    }


    // timed refresh inventory
    public static void refreshInv() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        ClientPlayNetworkHandler networkHandler = player.networkHandler;

        if (networkHandler != null) {
            ItemStack item = new ItemStack(Items.BEDROCK);
            NbtCompound nbt = new NbtCompound();
            nbt.putDouble("Inv Resync", Double.NaN);
            NbtComponent.set(DataComponentTypes.CUSTOM_DATA, item, nbt);
            ItemStackHash itemStackHash = ItemStackHash.fromItemStack(item, networkHandler.getComponentHasher());

            networkHandler.sendPacket(new ClickSlotC2SPacket(
                            player.playerScreenHandler.syncId,
                            player.playerScreenHandler.getRevision(),
                            (short) -999,
                            (byte) 2,
                            SlotActionType.QUICK_CRAFT,
                            new Int2ObjectOpenHashMap<>(),
                            itemStackHash
                    )
            );
        }
    }


    // might be useful, who knows
    public static List<Item> getItemsFromNames(List<String> strings) {
        List<Item> result = new ArrayList<>();
        for (String s : strings) {
            result.add(getItemFromName(s));
        }
        return result;
    }

    public static Item getItemFromName(String s) {
        try {
            return Registries.ITEM.get(Identifier.of(s));
        } catch (Exception e) {
            return null;
        }
    }
}

package xyz.crazyh.fabrictweaker.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

public class PacketUtils {
    private static final Queue<Packet<?>> QUEUED_PACKETS = new ArrayDeque<>();

    public static final Set<Class<?>> PACKETS_TO_DELAY = Set.of(
            PlayerActionC2SPacket.class,
            PlayerInputC2SPacket.class,
            PlayerInteractBlockC2SPacket.class,
            PlayerInteractItemC2SPacket.class,
            UpdateSelectedSlotC2SPacket.class
    );

    public static void addPacketToQueue(Packet<?> packet) {
        QUEUED_PACKETS.add(packet);
    }

    public static void clearQueue() {
        QUEUED_PACKETS.clear();
    }

    public static void sendDelayedPacket() {
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();

        QUEUED_PACKETS.iterator().forEachRemaining(packet -> networkHandler.sendPacket(packet));
    }
}

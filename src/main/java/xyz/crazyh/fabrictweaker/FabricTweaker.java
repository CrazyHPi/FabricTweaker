package xyz.crazyh.fabrictweaker;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.crazyh.fabrictweaker.config.Configs;
import xyz.crazyh.fabrictweaker.utils.InventoryUtils;
import xyz.crazyh.fabrictweaker.utils.PacketUtils;


public class FabricTweaker implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static MinecraftServer minecraftServer;

    private static int autoRefreshInventoryCounter = 0;

    @Override
    public void onInitialize() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
    }

    // server start event, triggered before server start
    public static void onServerLoaded(MinecraftServer server) {
        minecraftServer = server;
    }

    // server close event, called before server close
    public static void onServerClosed(MinecraftServer server) {
        if (minecraftServer != null) {

            minecraftServer = null;
        }
    }

    // client disconnection event
    public static void onClientDisconnected(DisconnectionInfo disconnectionInfo) {
        PacketUtils.clearQueue();
    }

    // game stop event, called before game client and server stop
    public static void onGameStop() {

    }

    // client tick event, called after client tick
    public static void onClientTick(MinecraftClient mc) {
        // AUTO_REFRESH_INV
        if (Configs.General.AUTO_REFRESH_INV.getBooleanValue()
                && autoRefreshInventoryCounter++ >= Configs.General.REFRESH_INV_INTERVAL.getIntegerValue()) {
            InventoryUtils.refreshInv();
            autoRefreshInventoryCounter = 0;
        }
    }
}

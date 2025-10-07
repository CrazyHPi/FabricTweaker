package xyz.crazyh.fabrictweaker;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FabricTweaker implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static MinecraftServer minecraftServer;

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

    // game stop event, called before game client and server stop
    public static void onGameStop() {

    }

    // client tick event, called after client tick
    public static void onClientTick(MinecraftClient mc) {

    }
}

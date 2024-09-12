package xyz.crazyh.fabrictweaker;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ModInitializer;
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

    public static void onServerLoaded(MinecraftServer server) {
        minecraftServer = server;
    }

    public static void onServerClosed(MinecraftServer server) {
        if (minecraftServer != null) {

            minecraftServer = null;
        }
    }

    public static void onGameStop() {

    }
}

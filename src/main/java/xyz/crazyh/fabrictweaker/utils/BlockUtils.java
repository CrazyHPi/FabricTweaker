package xyz.crazyh.fabrictweaker.utils;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    public static boolean isNearFence(PlayerEntity player) {
        MinecraftClient mc = MinecraftClient.getInstance();
        BlockPos pos = player.getBlockPos();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
//                BlockPos offset = pos.offset(Direction.Axis.X, i).offset(Direction.Axis.Z, j);
                Block block = mc.world.getBlockState(pos.offset(Direction.Axis.X, i).offset(Direction.Axis.Z, j)).getBlock();
                if (block instanceof FenceBlock || block instanceof WallBlock || block instanceof FenceGateBlock) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final List<Block> PERI_WALL_BLOCKS = new ArrayList<>();

    public static void updatePeriWallBlocks(List<String> blocks) {
        PERI_WALL_BLOCKS.clear();
        PERI_WALL_BLOCKS.addAll(getBlocksFromNames(blocks));
    }

    public static boolean isTopBlockBlacklisted(BlockPos pos) {
        ClientWorld world = MinecraftClient.getInstance().world;

        BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).down();
        Block topBlock = world.getBlockState(topPos).getBlock();
        // ignore
        if (topBlock instanceof SnowBlock) {
            topBlock = world.getBlockState(topPos.down()).getBlock();
        }

        return PERI_WALL_BLOCKS.contains(topBlock);
    }

    public static Block getBlockFromName(String blockName) {
        try {
            return Registries.BLOCK.get(Identifier.of(blockName));
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Block> getBlocksFromNames(List<String> blockNames) {
        List<Block> blocks = new ArrayList<>();

        for (String s : blockNames) {
            Block b = getBlockFromName(s);
            if (b != null) {
                blocks.add(b);
            }
        }
        return blocks;
    }
}

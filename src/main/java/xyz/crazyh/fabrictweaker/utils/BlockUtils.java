package xyz.crazyh.fabrictweaker.utils;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockUtils {
    public static boolean isNearFence(PlayerEntity player) {
        BlockPos pos = player.getBlockPos();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Block block = player.getWorld().getBlockState(pos.offset(Direction.Axis.X, i).offset(Direction.Axis.Y, j)).getBlock();
                if (block instanceof FenceBlock || block instanceof WallBlock || block instanceof FenceGateBlock) {
                    return true;
                }
            }
        }
        return false;
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableSlowdown;

import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(CobwebBlock.class)
public abstract class CobwebBlockMixin {
    @Inject(
            method = "onEntityCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"
            ),
            cancellable = true
    )
    private void cobwebNoSlowdown(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (DisableToggle.DISABLE_PLAYER_SLOWDOWN.getBooleanValue()) {
            ci.cancel();
        }
    }
}

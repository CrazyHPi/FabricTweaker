package xyz.crazyh.fabrictweaker.mixin.Tweaks.additionalBlockBreakingCooldown;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow private int blockBreakingCooldown;

    @Inject(
            method = "updateBlockBreakingProgress",
            at = @At(
                    value = "RETURN",
                    ordinal = 3
            )
    )
    private void addDelay(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.ADDITIONAL_BLOCK_BREAKING_COOLDOWN.getBooleanValue() && this.blockBreakingCooldown == 0) {
            // will make this configable soon tm
            this.blockBreakingCooldown = 3;
        }
    }
}

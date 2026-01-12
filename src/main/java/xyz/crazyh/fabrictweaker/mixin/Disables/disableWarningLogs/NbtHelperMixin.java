package xyz.crazyh.fabrictweaker.mixin.Disables.disableWarningLogs;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.nbt.NbtHelper;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(NbtHelper.class)
public abstract class NbtHelperMixin {

    // Unable to read property: {} with value: {} for blockstate: {}
    @WrapWithCondition(
            method = "withProperty",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;[Ljava/lang/Object;)V"
            )
    )
    private static boolean shouldWarnUnknownNbt(Logger instance, String s, Object[] objects) {
        return !DisableToggle.DISABLE_WARNING_LOGS.getBooleanValue();
    }
}

package xyz.crazyh.fabrictweaker.mixin.Disables.disableWitherSound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Inject(
            method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZJ)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelExplosionSound(double x, double y, double z, SoundEvent event, SoundCategory category, float volume, float pitch, boolean useDistance, long seed, CallbackInfo ci) {
        if (DisableToggle.DISABLE_WITHER_SOUND.getBooleanValue() &&
                (event == SoundEvents.ENTITY_WITHER_AMBIENT ||
                 event == SoundEvents.ENTITY_WITHER_HURT ||
                 event == SoundEvents.ENTITY_WITHER_DEATH ||
                 event == SoundEvents.ENTITY_WITHER_SHOOT)) {
            ci.cancel();
        }
    }
}

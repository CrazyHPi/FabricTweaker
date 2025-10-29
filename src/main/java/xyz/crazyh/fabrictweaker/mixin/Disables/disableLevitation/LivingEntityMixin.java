package xyz.crazyh.fabrictweaker.mixin.Disables.disableLevitation;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getStatusEffect", at = @At("HEAD"), cancellable = true)
    private void noLeviattion(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<StatusEffectInstance> cir) {
        if (((LivingEntity) (Object) this) instanceof ClientPlayerEntity
                && effect == StatusEffects.LEVITATION
                && DisableToggle.DISABLE_LEVITATION.getBooleanValue()) {
            cir.setReturnValue(null);
        }
    }
}

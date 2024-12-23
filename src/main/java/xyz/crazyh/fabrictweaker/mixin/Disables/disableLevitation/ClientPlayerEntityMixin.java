package xyz.crazyh.fabrictweaker.mixin.Disables.disableLevitation;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public boolean hasStatusEffect(RegistryEntry<StatusEffect> effect) {
        if (effect == StatusEffects.LEVITATION && DisableToggle.DISABLE_LEVITATION.getBooleanValue()) {
            return false;
        }

        return super.hasStatusEffect(effect);
    }
}

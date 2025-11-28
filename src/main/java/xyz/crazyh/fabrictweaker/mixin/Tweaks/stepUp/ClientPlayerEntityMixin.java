package xyz.crazyh.fabrictweaker.mixin.Tweaks.stepUp;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.crazyh.fabrictweaker.config.Configs;
import xyz.crazyh.fabrictweaker.config.FeatureToggle;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow public abstract boolean isSneaking();

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public float getStepHeight() {
        if (FeatureToggle.STEP_UP.getBooleanValue() && ! ((ClientPlayerEntity) (Object)this).isSneaking()){
            return (float) Configs.General.STEP_UP_HEIGHT.getDoubleValue();
        }
        return 0.6F;
    }
}

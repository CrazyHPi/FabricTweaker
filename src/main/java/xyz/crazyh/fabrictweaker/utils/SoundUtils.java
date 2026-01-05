package xyz.crazyh.fabrictweaker.utils;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import xyz.crazyh.fabrictweaker.config.DisableToggle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SoundUtils {
    private static final Set<SoundEvent> DISABLED_SOUND = new HashSet<>();

    public static SoundEvent getSoundEventFromStr(String sound) {
        return Registries.SOUND_EVENT.get(Identifier.of(sound));
    }

    public static boolean shouldMuteSound(SoundEvent sound) {
        return DisableToggle.DISABLE_SOUND.getBooleanValue() && DISABLED_SOUND.contains(sound);
    }

    public static void updateDisabledSound(List<String> soundList) {
        DISABLED_SOUND.clear();
        for (String sound : soundList) {
            SoundEvent soundEvent = getSoundEventFromStr(sound);
            if (soundEvent != null) {
                DISABLED_SOUND.add(soundEvent);
            }
        }
    }
}

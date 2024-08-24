package com.jahirtrap.walljump.proxy;

import com.jahirtrap.walljump.init.ModConfig;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class CommonProxy {
    public void setupCommon() {
    }

    public void setupClient() {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    private static class CommonHandler {
        @SubscribeEvent
        public static void onLivingFall(LivingFallEvent event) {
            float distance = event.getDistance();
            if (distance > 3 && distance <= ModConfig.minFallDistance) {
                event.setDistance(3.0F);
                event.getEntity().playSound(SoundEvents.GENERIC_SMALL_FALL, 0.5F, 1.0F);
            }
        }
    }
}

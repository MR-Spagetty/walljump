package com.jahirtrap.walljump.logic.mixin;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public abstract class PlayerFallDistanceMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @ModifyArg(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z"), index = 0)
    private float adjustFallDistance(float value) {
        if (value > 3 && value <= WallJumpModConfig.minFallDistance) {
            this.playSound(SoundEvents.GENERIC_SMALL_FALL, 0.5F, 1.0F);
            return 3.0F;
        }

        return value;
    }
}

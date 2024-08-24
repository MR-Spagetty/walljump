package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.init.ModEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SpeedBoostLogic {
    public static void doSpeedBoost(LocalPlayer pl) {
        if ((!ModConfig.enableEnchantments || !ModConfig.enableSpeedBoost) && (ModConfig.sprintSpeedBoost == 0 && ModConfig.elytraSpeedBoost == 0))
            return;
        int jumpBoostLevel = 0;
        MobEffectInstance jumpBoostEffect = pl.getEffect(MobEffects.JUMP);
        if (jumpBoostEffect != null) jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;
        pl.flyDist = (float) (pl.getSpeed() * (pl.isSprinting() ? 1 : 1.3) / 5) * (jumpBoostLevel * 0.5F + 1);

        Vec3 pos = pl.position();
        Vec3 look = pl.getLookAngle();
        Vec3 motion = pl.getDeltaMovement();

        if (pl.isFallFlying()) {
            if (pl.isShiftKeyDown()) {
                if (pl.getXRot() < 30f)
                    pl.setDeltaMovement(motion.subtract(motion.scale(0.05)));
            } else if (Minecraft.getInstance().options.keySprint.isDown() && pl.input.hasForwardImpulse()) {
                float elytraSpeedBoost = (float) ModConfig.elytraSpeedBoost + (getEquipmentBoost(pl, EquipmentSlot.CHEST) * (float) ModConfig.speedBoostMultiplier);
                Vec3 boost = new Vec3(look.x, look.y, look.z).normalize().scale(elytraSpeedBoost);
                if (motion.length() <= boost.length())
                    pl.setDeltaMovement(motion.add(boost.scale(0.05)));
                if (boost.length() > 0.5)
                    pl.level().addParticle(ParticleTypes.FIREWORK, pos.x, pos.y, pos.z, 0, 0, 0);
            }
        } else if (pl.isSprinting()) {
            float sprintSpeedBoost = (float) (ModConfig.sprintSpeedBoost + (getEquipmentBoost(pl, EquipmentSlot.FEET) * (ModConfig.speedBoostMultiplier / 2)));
            if (!pl.onGround()) sprintSpeedBoost /= 3.125F;

            Vec3 boost = new Vec3(look.x, 0.0, look.z).scale(sprintSpeedBoost * 0.125F);
            pl.setDeltaMovement(motion.add(boost));
        }
    }

    private static int getEquipmentBoost(LocalPlayer pl, EquipmentSlot slot) {
        if (!ModConfig.enableEnchantments || !ModConfig.enableSpeedBoost)
            return 0;
        ItemStack stack = pl.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            if (enchantments.containsKey(ModEnchantments.SPEED_BOOST.get()))
                return enchantments.get(ModEnchantments.SPEED_BOOST.get());
        }

        return 0;
    }
}

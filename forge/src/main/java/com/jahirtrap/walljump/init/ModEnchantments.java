package com.jahirtrap.walljump.init;

import com.jahirtrap.walljump.enchantment.DoubleJumpEnchantment;
import com.jahirtrap.walljump.enchantment.SpeedBoostEnchantment;
import com.jahirtrap.walljump.enchantment.WallJumpEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.jahirtrap.walljump.WallJumpMod.MODID;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registry.ENCHANTMENT_REGISTRY, MODID);

    public static final RegistryObject<Enchantment> WALL_JUMP = ENCHANTMENTS.register("wall_jump", WallJumpEnchantment::new);
    public static final RegistryObject<Enchantment> DOUBLE_JUMP = ENCHANTMENTS.register("double_jump", DoubleJumpEnchantment::new);
    public static final RegistryObject<Enchantment> SPEED_BOOST = ENCHANTMENTS.register("speed_boost", SpeedBoostEnchantment::new);

    public static void init(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }
}

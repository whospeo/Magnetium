package com.whospeo.magnetium.item;

import com.whospeo.magnetium.Magnetium;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup MAGNETIUM_ITEMS    = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Magnetium.MOD_ID, "magnetium_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.MAGNET))
                    .displayName(Text.translatable("itemgroup.magnetium.magnetium_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.NORTH_POLE);
                        entries.add(ModItems.SOUTH_POLE);
                        entries.add(ModItems.MAGNET);
                        entries.add(ModItems.MAGNET_TIER_2);
                        entries.add(ModItems.MAGNET_TIER_3);
                        entries.add(ModItems.MAGNETIC_HAMMER);
                        entries.add(ModItems.IRON_STICK);
                    }).build());

    public static void registerItemGroups() {
        Magnetium.LOGGER.info("Registering Item Groups for " + Magnetium.MOD_ID);
    }
}

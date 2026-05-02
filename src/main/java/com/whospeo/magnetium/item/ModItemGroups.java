package com.whospeo.magnetium.item;

import com.whospeo.magnetium.Magnetium;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ModItemGroups {
    public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(Magnetium.MOD_ID, "creative_tab")
    );
    public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.MAGNET))
            .title(Component.translatable("itemgroup.magnetium.magnetium_items"))
            .displayItems((params, output) -> {
                output.accept(ModItems.NORTH_POLE);
                output.accept(ModItems.SOUTH_POLE);
                output.accept(ModItems.IRON_STICK);
                output.accept(ModItems.MAGNET);
                output.accept(ModItems.MAGNET_TIER_2);
                output.accept(ModItems.MAGNET_TIER_3);
                output.accept(ModItems.MAGNETIC_HAMMER);
            })
            .build();

    public static void register() {
        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                CUSTOM_CREATIVE_TAB_KEY,
                CUSTOM_CREATIVE_TAB
        );
    }
}

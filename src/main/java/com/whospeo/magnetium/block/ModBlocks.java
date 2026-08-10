package com.whospeo.magnetium.block;

import com.whospeo.magnetium.Magnetium;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModBlocks {

    private static Block registerBlock(String name, Block block) {
        ResourceKey<Block> blockKey = ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(Magnetium.MOD_ID, name)
        );
        return Registry.register(
                BuiltInRegistries.BLOCK,
                blockKey,
                block
        );
    }


    private static void registerBlockItem(String name, Block block) {
        ResourceKey<Item> itemKey = ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(Magnetium.MOD_ID, name)
        );
        Registry.register(
                BuiltInRegistries.ITEM,
                itemKey,
                new BlockItem(block, new Item.Properties().setId(itemKey))
        );
    }

    public static void registerBlocks() {
        Magnetium.LOGGER.info("[Magnetium] Registering Blocks");
    }
}

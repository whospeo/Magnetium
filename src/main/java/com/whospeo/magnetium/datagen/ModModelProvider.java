package com.whospeo.magnetium.datagen;

import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.MAGNET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MAGNET_TIER_2, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MAGNET_TIER_3, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.NORTH_POLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SOUTH_POLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.IRON_STICK, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.MAGNETIC_HAMMER, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}

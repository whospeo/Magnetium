package com.whospeo.magnetium.datagen;

import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.MAGNET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGNET_TIER_2, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGNET_TIER_3, Models.GENERATED);

        itemModelGenerator.register(ModItems.NORTH_POLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUTH_POLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_STICK, Models.GENERATED);

        itemModelGenerator.register(ModItems.MAGNETIC_HAMMER, Models.HANDHELD);
    }
}

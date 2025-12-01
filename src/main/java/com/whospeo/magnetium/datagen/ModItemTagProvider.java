package com.whospeo.magnetium.datagen;

import com.whospeo.magnetium.item.ModItems;
import com.whospeo.magnetium.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.MAGNETIC_ITEMS)
                .add(ModItems.MAGNET)
                .add(ModItems.MAGNET_TIER_2)
                .add(ModItems.MAGNET_TIER_3)
                .add(ModItems.NORTH_POLE)
                .add(ModItems.SOUTH_POLE)
                .add(ModItems.MAGNETIC_HAMMER)
                .add(ModItems.IRON_STICK);
    }
}

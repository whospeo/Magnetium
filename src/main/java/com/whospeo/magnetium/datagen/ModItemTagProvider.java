package com.whospeo.magnetium.datagen;

import com.whospeo.magnetium.Magnetium;
import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public static final TagKey<Item> MAGNETIC_ITEMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Magnetium.MOD_ID, "magnetic_items"));

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup){
        valueLookupBuilder(MAGNETIC_ITEMS)
                .add(ModItems.MAGNET)
                .add(ModItems.MAGNET_TIER_2)
                .add(ModItems.MAGNET_TIER_3)
                .add(ModItems.NORTH_POLE)
                .add(ModItems.SOUTH_POLE)
                .add(ModItems.IRON_STICK)
                .add(ModItems.MAGNETIC_HAMMER);
    }
}

package com.whospeo.magnetium.datagen;

import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {

                createShaped(RecipeCategory.MISC, ModItems.MAGNET)
                        .pattern("N S")
                        .pattern("III")
                        .pattern("   ")
                        .input('N', ModItems.NORTH_POLE)
                        .input('S', ModItems.SOUTH_POLE)
                        .input('I', Items.IRON_INGOT)
                        .criterion(hasItem(ModItems.NORTH_POLE), conditionsFromItem(ModItems.NORTH_POLE))
                        .criterion(hasItem(ModItems.SOUTH_POLE), conditionsFromItem(ModItems.SOUTH_POLE))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.MISC, ModItems.MAGNET_TIER_2)
                        .pattern("GGG")
                        .pattern("GMG")
                        .pattern("GGG")
                        .input('G', Items.GOLD_INGOT)
                        .input('M', ModItems.MAGNET)
                        .criterion(hasItem(ModItems.MAGNET), conditionsFromItem(ModItems.MAGNET))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.MISC, ModItems.MAGNET_TIER_3)
                        .pattern("DDD")
                        .pattern("DMD")
                        .pattern("DDD")
                        .input('D', Items.DIAMOND)
                        .input('M', ModItems.MAGNET_TIER_2)
                        .criterion(hasItem(ModItems.MAGNET_TIER_2), conditionsFromItem(ModItems.MAGNET_TIER_2))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.MISC, ModItems.MAGNETIC_HAMMER)
                        .pattern("NIS")
                        .pattern(" I ")
                        .pattern(" I ")
                        .input('N', ModItems.NORTH_POLE)
                        .input('S', ModItems.SOUTH_POLE)
                        .input('I', ModItems.IRON_STICK)
                        .criterion(hasItem(ModItems.NORTH_POLE), conditionsFromItem(ModItems.NORTH_POLE))
                        .criterion(hasItem(ModItems.SOUTH_POLE), conditionsFromItem(ModItems.SOUTH_POLE))
                        .offerTo(recipeExporter);

                createShaped(RecipeCategory.MISC, ModItems.IRON_STICK)
                        .pattern(" I ")
                        .pattern(" I ")
                        .pattern("   ")
                        .input('I', Items.IRON_INGOT)
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(recipeExporter);


                createShapeless(RecipeCategory.MISC, ModItems.NORTH_POLE)
                        .input(Items.RED_DYE)
                        .input(Items.IRON_INGOT)
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(recipeExporter);

                createShapeless(RecipeCategory.MISC, ModItems.SOUTH_POLE)
                        .input(Items.BLUE_DYE)
                        .input(Items.IRON_INGOT)
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(recipeExporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Magnetium Recipes";
    }
}

package com.whospeo.magnetium.datagen;

import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shaped(RecipeCategory.MISC, ModItems.MAGNET)
                        .pattern("N S")
                        .pattern("III")
                        .pattern("   ")
                        .define('N', ModItems.NORTH_POLE)
                        .define('S', ModItems.SOUTH_POLE)
                        .define('I', net.minecraft.world.item.Items.IRON_INGOT)
                        .unlockedBy(getHasName(ModItems.NORTH_POLE), has(ModItems.NORTH_POLE))
                        .unlockedBy(getHasName(ModItems.SOUTH_POLE), has(ModItems.SOUTH_POLE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.MAGNET_TIER_2)
                        .pattern("GGG")
                        .pattern("GMG")
                        .pattern("GGG")
                        .define('G', net.minecraft.world.item.Items.GOLD_INGOT)
                        .define('M', ModItems.MAGNET)
                        .unlockedBy(getHasName(ModItems.MAGNET), has(ModItems.MAGNET))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.MAGNET_TIER_3)
                        .pattern("DDD")
                        .pattern("DMD")
                        .pattern("DDD")
                        .define('D', net.minecraft.world.item.Items.DIAMOND)
                        .define('M', ModItems.MAGNET_TIER_2)
                        .unlockedBy(getHasName(ModItems.MAGNET_TIER_2), has(ModItems.MAGNET_TIER_2))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.MAGNETIC_HAMMER)
                        .pattern("NIS")
                        .pattern(" I ")
                        .pattern(" I ")
                        .define('N', ModItems.NORTH_POLE)
                        .define('S', ModItems.SOUTH_POLE)
                        .define('I', ModItems.IRON_STICK)
                        .unlockedBy(getHasName(ModItems.NORTH_POLE), has(ModItems.NORTH_POLE))
                        .unlockedBy(getHasName(ModItems.SOUTH_POLE), has(ModItems.SOUTH_POLE))
                        .save(output);

                shaped(RecipeCategory.MISC, ModItems.IRON_STICK)
                        .pattern(" I ")
                        .pattern(" I ")
                        .pattern("   ")
                        .define('I', net.minecraft.world.item.Items.IRON_INGOT)
                        .unlockedBy(getHasName(net.minecraft.world.item.Items.IRON_INGOT), has(net.minecraft.world.item.Items.IRON_INGOT))
                        .save(output);

                shapeless(RecipeCategory.MISC, ModItems.NORTH_POLE)
                        .requires(net.minecraft.world.item.Items.RED_DYE)
                        .requires(net.minecraft.world.item.Items.IRON_INGOT)
                        .unlockedBy(getHasName(net.minecraft.world.item.Items.IRON_INGOT), has(net.minecraft.world.item.Items.IRON_INGOT))
                        .save(output);

                shapeless(RecipeCategory.MISC, ModItems.SOUTH_POLE)
                        .requires(net.minecraft.world.item.Items.BLUE_DYE)
                        .requires(net.minecraft.world.item.Items.IRON_INGOT)
                        .unlockedBy(getHasName(net.minecraft.world.item.Items.IRON_INGOT), has(net.minecraft.world.item.Items.IRON_INGOT))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Magnetium Recipes";
    }
}

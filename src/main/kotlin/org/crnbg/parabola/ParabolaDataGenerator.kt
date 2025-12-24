package org.crnbg.parabola

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.data.client.Models
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import org.crnbg.parabola.item.ParabolaItems
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.VanillaRecipeProvider
import net.minecraft.util.Identifier

object ParabolaDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        
        // Model provider
        pack.addProvider { output, registriesFuture ->
            object : net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider(output) {
                override fun generateItemModels(itemModelGenerator: net.minecraft.data.client.ItemModelGenerator) {
                    itemModelGenerator.register(ParabolaItems.CRIMSON_SCYTHE, Models.HANDHELD)
                }
                
                override fun generateBlockStateModels(blockStateModelGenerator: net.minecraft.data.client.BlockStateModelGenerator) {
                    // No blocks to register
                }
            }
        }
        
        // Recipe provider
        pack.addProvider { output, registriesFuture ->
            object : RecipeProvider(output) {
                override fun generate(registryManager: net.minecraft.recipe.RecipeExporter) {
                    // Crimson Scythe Recipe
                    ShapedRecipeJsonBuilder.create(registryManager, RecipeCategory.COMBAT, ParabolaItems.CRIMSON_SCYTHE, 1)
                        .input('A', Items.NETHERITE_AXE)
                        .input('S', Items.NETHERITE_SWORD)
                        .pattern(" A")
                        .pattern(" S")
                        .pattern("S ")
                        .criterion(VanillaRecipeProvider.hasItem(Items.NETHERITE_AXE), VanillaRecipeProvider.conditionsFromItem(Items.NETHERITE_AXE))
                        .criterion(VanillaRecipeProvider.hasItem(Items.NETHERITE_SWORD), VanillaRecipeProvider.conditionsFromItem(Items.NETHERITE_SWORD))
                        .offerTo(registryManager, Identifier.of("parabola", "crimson_scythe"))
                }
            }
        }
    }
}

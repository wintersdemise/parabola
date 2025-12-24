package org.crnbg.parabola

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import net.minecraft.data.recipe.RecipeExporter
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import org.crnbg.parabola.item.ParabolaItems

object ParabolaDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        fabricDataGenerator.addProvider(ParabolaModelProvider(fabricDataGenerator))
        fabricDataGenerator.addProvider(ParabolaRecipeProvider(fabricDataGenerator))
    }
}

class ParabolaModelProvider(private val dataGenerator: FabricDataGenerator) : FabricModelProvider(dataGenerator) {
    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(ParabolaItems.CRIMSON_SCYTHE, Models.HANDHELD)
    }

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        // No blocks to register
    }
}

class ParabolaRecipeProvider(private val dataGenerator: FabricDataGenerator) : FabricRecipeProvider(dataGenerator) {
    override fun generate(exporter: RecipeExporter) {
        // Crimson Scythe Recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ParabolaItems.CRIMSON_SCYTHE)
            .pattern(" #")
            .pattern(" X")
            .pattern("X ")
            .where('#', Items.NETHERITE_AXE)
            .where('X', Items.NETHERITE_SWORD)
            .criterion(hasItem(Items.NETHERITE_AXE), conditionsFromItem(Items.NETHERITE_AXE))
            .criterion(hasItem(Items.NETHERITE_SWORD), conditionsFromItem(Items.NETHERITE_SWORD))
            .offerTo(exporter)
    }
}

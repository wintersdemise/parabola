package org.crnbg.parabola

import net.fabricmc.fabric.api.client.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.client.data.BlockStateModelGenerator
import net.minecraft.client.data.ItemModelGenerator
import net.minecraft.client.data.Models
import net.minecraft.data.recipe.RecipeExporter
import net.minecraft.data.recipe.RecipeGenerator
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.Identifier
import org.crnbg.parabola.item.ParabolaItems

object ParabolaDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()

        pack.addProvider { output ->
           object : FabricModelProvider(output as FabricDataOutput) {
                override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
                }

                override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
                    itemModelGenerator.register(ParabolaItems.CRIMSON_SCYTHE, Models.HANDHELD)
                }
            }
        }

        pack.addProvider { output, registriesFuture ->
           object : FabricRecipeProvider(output, registriesFuture) {
               override fun getName(): String {
                   return "Parabola Recipes"
               }

               override fun getRecipeGenerator(
                   registryLookup: RegistryWrapper.WrapperLookup,
                   exporter: RecipeExporter,
               ): RecipeGenerator {
                   val recipeKey = RegistryKey.of(
                       RegistryKeys.RECIPE,
                       Identifier.of(output.modId, "crimson_scythe"),
                   )

                   return object : RecipeGenerator(registryLookup, exporter) {
                       override fun generate() {
                           createShaped(RecipeCategory.COMBAT, ParabolaItems.CRIMSON_SCYTHE)
                               .input('A', Items.NETHERITE_AXE)
                               .input('S', Items.NETHERITE_SWORD)
                               .pattern(" A")
                               .pattern(" S")
                               .pattern("S ")
                               .criterion(hasItem(Items.NETHERITE_AXE), conditionsFromItem(Items.NETHERITE_AXE))
                               .criterion(hasItem(Items.NETHERITE_SWORD), conditionsFromItem(Items.NETHERITE_SWORD))
                               .offerTo(exporter, recipeKey)
                       }
                   }
               }
           }
        }
    }
}

package org.crnbg.parabola

import net.fabricmc.api.ModInitializer
import org.crnbg.parabola.item.ParabolaItems
import org.slf4j.LoggerFactory

object Parabola : ModInitializer {
    private val logger = LoggerFactory.getLogger("parabola")

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        ParabolaItems.initialize()
        logger.info("Hello Fabric world!")
        logger.info("Crimson Scythe registered!")
    }
}
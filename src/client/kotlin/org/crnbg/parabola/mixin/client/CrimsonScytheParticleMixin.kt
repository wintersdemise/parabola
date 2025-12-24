package org.crnbg.parabola.mixin.client

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.EquipmentSlot
import net.minecraft.particle.ParticleTypes
import org.crnbg.parabola.item.ParabolaItems
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient::class)
class CrimsonScytheParticleMixin {
    @Shadow
    var player: net.minecraft.client.network.ClientPlayerEntity? = null

    @Shadow
    var world: ClientWorld? = null

    @Inject(at = [At("HEAD")], method = ["tick()V"])
    fun tickParticles(ci: CallbackInfo) {
        val client = MinecraftClient.getInstance() ?: return
        val player = client.player ?: return
        val world = client.world ?: return

        // Check if player is holding Crimson Scythe
        val mainHand = player.getEquippedStack(EquipmentSlot.MAINHAND)
        if (mainHand.item == ParabolaItems.CRIMSON_SCYTHE) {
            // Spawn red particles around the player
            if (world.random.nextFloat() < 0.4f) {
                val x = player.x + (world.random.nextDouble() - 0.5) * 0.5
                val y = player.y + world.random.nextDouble() * 1.8
                val z = player.z + (world.random.nextDouble() - 0.5) * 0.5

                world.addParticle(
                    ParticleTypes.DRAGON_BREATH,
                    x, y, z,
                    0.0, 0.0, 0.0
                )
            }
        }
    }
}

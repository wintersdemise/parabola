package org.crnbg.parabola.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class CrimsonScytheItem extends Item {
    private static final float SWEEP_RADIUS = 4.0f;
    private static final int ABSORPTION_DURATION = 120;
    private static final int ABSORPTION_AMPLIFIER = 0;

    public CrimsonScytheItem() {
        super(new Settings().maxCount(1).maxDamage(2031));
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseDamage, DamageSource damageSource) {
        return baseDamage + 4.0f;
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.setDamage(stack.getDamage() + 1);
        
        if (attacker instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.ABSORPTION,
                ABSORPTION_DURATION,
                ABSORPTION_AMPLIFIER
            ));
        }
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState blockState, BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && blockState.getHardness(world, pos) != 0.0f) {
            stack.setDamage(stack.getDamage() + 1);
        }

        if (miner instanceof PlayerEntity player && world instanceof ServerWorld serverWorld) {
            Box box = new Box(pos).expand(SWEEP_RADIUS, SWEEP_RADIUS, SWEEP_RADIUS);
            List<Entity> nearbyMobs = world.getOtherEntities(miner, box, entity ->
                entity instanceof LivingEntity && entity != miner && entity.isAlive()
            );

            for (Entity entity : nearbyMobs) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.damage(serverWorld, player.getDamageSources().playerAttack(player), 3.0f);
                    miner.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.ABSORPTION,
                        ABSORPTION_DURATION,
                        ABSORPTION_AMPLIFIER
                    ));
                }
            }
        }
        return true;
    }
}

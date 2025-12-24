package org.crnbg.parabola.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ParabolaItems {
    public static final CrimsonScytheItem CRIMSON_SCYTHE = new CrimsonScytheItem();

    static {
        Registry.register(Registries.ITEM, new Identifier("parabola", "crimson_scythe"), CRIMSON_SCYTHE);
    }

    public static void initialize() {
        // Items are auto-registered via static initialization
    }
}

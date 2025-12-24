package org.crnbg.parabola.item;

import net.minecraft.item.Item;

public class ParabolaItems {
    public static final CrimsonScytheItem CRIMSON_SCYTHE = new CrimsonScytheItem();

    static {
        CRIMSON_SCYTHE.setRegistryName("parabola", "crimson_scythe");
    }

    public static void initialize() {
        // Items are auto-registered via static initialization
    }
}

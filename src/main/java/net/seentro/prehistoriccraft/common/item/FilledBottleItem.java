package net.seentro.prehistoriccraft.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class FilledBottleItem extends Item {
    public Fluid content;

    public FilledBottleItem(Properties properties, Fluid content) {
        super(properties);
        this.content = content;
    }
}

package net.seentro.prehistoriccraft.common.item;

import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.CapabilityHooks;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class FilledBottleItem extends Item {
    public Fluid content;

    public FilledBottleItem(Properties properties, Fluid content) {
        super(properties);
        this.content = content;
    }
}

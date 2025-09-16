package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.Supplier;

public class PrehistoricEnumExtensions {
    public static final EnumProxy<Boat.Type> DAWN_REDWOOD = new EnumProxy<>(Boat.Type.class,
            (Supplier<Block>) () -> PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get(),
            "prehistoriccraft:dawn_redwood",
            (Supplier<Item>) () -> PrehistoricItems.DAWN_REDWOOD_BOAT.get(),
            (Supplier<Item>) () -> PrehistoricItems.DAWN_REDWOOD_CHEST_BOAT.get(),
            (Supplier<Item>) () -> Items.STICK, false);
}

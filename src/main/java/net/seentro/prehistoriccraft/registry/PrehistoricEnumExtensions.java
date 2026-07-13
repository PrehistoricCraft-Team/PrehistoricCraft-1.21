package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.Supplier;

public class PrehistoricEnumExtensions {
    public static final EnumProxy<Boat.Type> DAWN_REDWOOD_ENUM_PROXY = new EnumProxy<>(Boat.Type.class,
            PrehistoricBlocks.DAWN_REDWOOD_PLANKS,
            "prehistoriccraft:dawn_redwood",
            PrehistoricItems.DAWN_REDWOOD_BOAT,
            PrehistoricItems.DAWN_REDWOOD_CHEST_BOAT,
            (Supplier<Item>) () -> Items.STICK,
            false);
}

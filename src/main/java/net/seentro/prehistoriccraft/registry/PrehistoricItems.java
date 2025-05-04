package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PrehistoricCraft.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, PrehistoricCraft.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FOSSIL_TAB = CREATIVE_MODE_TABS.register("fossil_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get()))
            .title(Component.translatable("tabs.prehistoriccraft.fossil_tab")).displayItems((displayParameters, output) -> {
                output.accept(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricItems.CAMBRIAN_FOSSIL.get());
                output.accept(PrehistoricItems.CARBONIFEROUS_FOSSIL.get());
                output.accept(PrehistoricItems.CRETACEOUS_FOSSIL.get());
                output.accept(PrehistoricItems.DEVONIAN_FOSSIL.get());
                output.accept(PrehistoricItems.JURASSIC_FOSSIL.get());
                output.accept(PrehistoricItems.NEOGENE_FOSSIL.get());
                output.accept(PrehistoricItems.ORDOVICIAN_FOSSIL.get());
                output.accept(PrehistoricItems.PALEOGENE_FOSSIL.get());
                output.accept(PrehistoricItems.PERMIAN_FOSSIL.get());
                output.accept(PrehistoricItems.PRECAMBRIAN_FOSSIL.get());
                output.accept(PrehistoricItems.SILURIAN_FOSSIL.get());
                output.accept(PrehistoricItems.TRIASSIC_FOSSIL.get());
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

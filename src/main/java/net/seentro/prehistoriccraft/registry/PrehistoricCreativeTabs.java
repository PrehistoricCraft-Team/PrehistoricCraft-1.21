package net.seentro.prehistoriccraft.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;

import java.util.function.Supplier;

public class PrehistoricCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PrehistoricCraft.MODID);

    public static final Supplier<CreativeModeTab> PREHISTORICCRAFT_FOSSILS = CREATIVE_MODE_TABS.register("prehistoriccraft_fossils", () -> CreativeModeTab.builder().icon(() -> new ItemStack(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get()))
            .title(Component.translatable("tabs.prehistoriccraft.prehistoriccraft_fossils")).displayItems((displayParameters, output) -> {
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
                output.accept(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get());
                output.accept(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get());
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
                output.accept(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE.get());
                output.accept(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE.get());

            }).build());

    public static final Supplier<CreativeModeTab> PREHISTORICCRAFT_NATURAL_BLOCKS = CREATIVE_MODE_TABS.register("prehistoriccraft_natural_blocks", () -> CreativeModeTab.builder().icon(() -> new ItemStack(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get()))
            .title(Component.translatable("tabs.prehistoriccraft.prehistoriccraft_natural_blocks")).displayItems((displayParameters, output) -> {
                output.accept(PrehistoricBlocks.GYPSUM_CRYSTAL.get());
                output.accept(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get());
                output.accept(PrehistoricBlocks.CRACKED_DIRT.get());
            }).build());

    public static final Supplier<CreativeModeTab> PREHISTORICCRAFT_ITEMS = CREATIVE_MODE_TABS.register("prehistoriccraft_items", () -> CreativeModeTab.builder().icon(() -> new ItemStack(PrehistoricItems.EXCAVATOR_PICKAXE.get()))
            .title(Component.translatable("tabs.prehistoriccraft.prehistoriccraft_items")).displayItems((displayParameters, output) -> {
                output.accept(PrehistoricItems.EXCAVATOR_PICKAXE.get());
                output.accept(PrehistoricItems.MAGNIFYING_GLASS.get());
                output.accept(PrehistoricItems.PLASTER_WRAP.get());
                output.accept(PrehistoricItems.GYPSUM_POWDER.get());
                output.accept(PrehistoricItems.PLASTER_POWDER.get());
                output.accept(PrehistoricItems.AMBER.get());
                output.accept(PrehistoricItems.ANIMAL_TISSUE.get());
                output.accept(PrehistoricItems.PLANT_TISSUE.get());
                output.accept(PrehistoricItems.BLOOD_CELL.get());
                output.accept(PrehistoricItems.OBSIDIAN_PLATE.get());
                output.accept(PrehistoricItems.REINFORCED_OBSIDIAN_PLATE.get());
                output.accept(PrehistoricItems.GOLD_PIPE.get());
                output.accept(PrehistoricItems.BLOB_OF_BLICE.get());
                output.accept(PrehistoricItems.BOTTLE_OF_BLICE.get());
                output.accept(PrehistoricItems.VIAL.get());
                output.accept(PrehistoricItems.JAR.get());
                output.accept(PrehistoricItems.SYRINGE.get());
                output.accept(PrehistoricItems.SULFUR.get());
            }).build());

    public static final Supplier<CreativeModeTab> PREHISTORICCRAFT_BLOCKS = CREATIVE_MODE_TABS.register("prehistoriccraft_blocks", () -> CreativeModeTab.builder().icon(() -> new ItemStack(PrehistoricBlocks.AMBER_BLOCK.get()))
            .title(Component.translatable("tabs.prehistoriccraft.prehistoriccraft_blocks")).displayItems((displayParameters, output) -> {
                output.accept(PrehistoricBlocks.AMBER_BLOCK.get());
                output.accept(PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get());
                output.accept(PrehistoricBlocks.SULFUR_ORE.get());
                output.accept(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE.get());
            }).build());

    public static final Supplier<CreativeModeTab> PREHISTORICCRAFT_FUNCTIONAL_BLOCKS = CREATIVE_MODE_TABS.register("prehistoriccraft_functional_blocks", () -> CreativeModeTab.builder().icon(() -> new ItemStack(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get()))
            .title(Component.translatable("tabs.prehistoriccraft.prehistoriccraft_functional_blocks")).displayItems((displayParameters, output) -> {
                output.accept(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());
                output.accept(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get());
            }).build());


    public static void register(IEventBus eventBus) {
        PrehistoricCreativeTabs.CREATIVE_MODE_TABS.register(eventBus);
    }
}

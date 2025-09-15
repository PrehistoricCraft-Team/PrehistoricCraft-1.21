package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.item.AcidCleaningChamberBlockItem;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.item.TissueExtractionChamberBlockItem;
import net.seentro.prehistoriccraft.common.item.ExcavatorPickaxeItem;
import net.seentro.prehistoriccraft.common.item.FossilItem;
import net.seentro.prehistoriccraft.common.item.TissueItem;


public class PrehistoricItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PrehistoricCraft.MODID);

    /* NATURE */

    //DAWN REDWOOD
    public static final DeferredItem<Item> DAWN_REDWOOD_SIGN = ITEMS.register("dawn_redwood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), PrehistoricBlocks.DAWN_REDWOOD_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> DAWN_REDWOOD_HANGING_SIGN = ITEMS.register("dawn_redwood_hanging_sign", () -> new HangingSignItem(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> DAWN_REDWOOD_BOAT = ITEMS.register("dawn_redwood_boat", () -> new BoatItem(false, Boat.Type.OAK, new Item.Properties().stacksTo(1)));

    /* FOSSILS */
    public static final DeferredItem<Item> PRECAMBRIAN_FOSSIL = ITEMS.register("precambrian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CAMBRIAN_FOSSIL = ITEMS.register("cambrian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> ORDOVICIAN_FOSSIL = ITEMS.register("ordovician_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> SILURIAN_FOSSIL = ITEMS.register("silurian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> DEVONIAN_FOSSIL = ITEMS.register("devonian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CARBONIFEROUS_FOSSIL = ITEMS.register("carboniferous_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PERMIAN_FOSSIL = ITEMS.register("permian_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> TRIASSIC_FOSSIL = ITEMS.register("triassic_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> JURASSIC_FOSSIL = ITEMS.register("jurassic_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CRETACEOUS_FOSSIL = ITEMS.register("cretaceous_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PALEOGENE_FOSSIL = ITEMS.register("paleogene_fossil", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> NEOGENE_FOSSIL   = ITEMS.register("neogene_fossil", () -> new FossilItem(new Item.Properties()));

    /* FOSSIL SAMPLES */
    public static final DeferredItem<Item> PRECAMBRIAN_FOSSIL_SAMPLE = ITEMS.register("precambrian_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CAMBRIAN_FOSSIL_SAMPLE = ITEMS.register("cambrian_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> ORDOVICIAN_FOSSIL_SAMPLE = ITEMS.register("ordovician_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> SILURIAN_FOSSIL_SAMPLE = ITEMS.register("silurian_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> DEVONIAN_FOSSIL_SAMPLE = ITEMS.register("devonian_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CARBONIFEROUS_FOSSIL_SAMPLE = ITEMS.register("carboniferous_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PERMIAN_FOSSIL_SAMPLE = ITEMS.register("permian_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> TRIASSIC_FOSSIL_SAMPLE = ITEMS.register("triassic_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> JURASSIC_FOSSIL_SAMPLE = ITEMS.register("jurassic_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> CRETACEOUS_FOSSIL_SAMPLE = ITEMS.register("cretaceous_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> PALEOGENE_FOSSIL_SAMPLE = ITEMS.register("paleogene_fossil_sample", () -> new FossilItem(new Item.Properties()));
    public static final DeferredItem<Item> NEOGENE_FOSSIL_SAMPLE = ITEMS.register("neogene_fossil_sample", () -> new FossilItem(new Item.Properties()));

    /* TISSUE */
    public static final DeferredItem<Item> ANIMAL_TISSUE = ITEMS.register("animal_tissue", () -> new TissueItem(new Item.Properties()));
    public static final DeferredItem<Item> PLANT_TISSUE = ITEMS.register("plant_tissue", () -> new TissueItem(new Item.Properties()));
    public static final DeferredItem<Item> BLOOD_CELL = ITEMS.register("blood_cell", () -> new TissueItem(new Item.Properties()));

    /* TOOLS */
    public static final DeferredItem<Item> EXCAVATOR_PICKAXE   = ITEMS.register("excavator_pickaxe", () -> new ExcavatorPickaxeItem(new Item.Properties().durability(250)));
    public static final DeferredItem<Item> MAGNIFYING_GLASS   = ITEMS.register("magnifying_glass", () -> new Item(new Item.Properties().durability(192)));

    /* GYPSUM & PLASTER */
    public static final DeferredItem<Item> GYPSUM_POWDER = ITEMS.register("gypsum_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLASTER_POWDER = ITEMS.register("plaster_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLASTER_WRAP = ITEMS.register("plaster_wrap", () -> new Item(new Item.Properties()));

    /* ORES */
    public static final DeferredItem<Item> AMBER = ITEMS.register("amber", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties()));

    /* BLOCK ENTITIES */
    public static final DeferredItem<Item> ACID_CLEANING_CHAMBER = ITEMS.register("acid_cleaning_chamber", () -> new AcidCleaningChamberBlockItem(new Item.Properties()));
    public static final DeferredItem<Item> TISSUE_EXTRACTION_CHAMBER = ITEMS.register("tissue_extraction_chamber", () -> new TissueExtractionChamberBlockItem(new Item.Properties()));

    /* CRAFTING MATERIALS */
    public static final DeferredItem<Item> OBSIDIAN_PLATE = ITEMS.register("obsidian_plate", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REINFORCED_OBSIDIAN_PLATE = ITEMS.register("reinforced_obsidian_plate", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_PIPE = ITEMS.register("gold_pipe", () -> new Item(new Item.Properties()));

    /* BLICE */
    public static final DeferredItem<Item> BLOB_OF_BLICE = ITEMS.register("blob_of_blice", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BOTTLE_OF_BLICE = ITEMS.register("bottle_of_blice", () -> new Item(new Item.Properties()));

    /* GLASS INSTRUMENTS */
    public static final DeferredItem<Item> VIAL = ITEMS.register("vial", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JAR = ITEMS.register("jar", () -> new Item(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

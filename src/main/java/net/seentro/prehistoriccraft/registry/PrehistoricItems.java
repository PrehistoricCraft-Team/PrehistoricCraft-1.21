package net.seentro.prehistoriccraft.registry;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.block.acidCleaningChamber.item.AcidCleaningChamberBlockItem;
import net.seentro.prehistoriccraft.common.block.tissueExtractionChamber.item.TissueExtractionChamberBlockItem;
import net.seentro.prehistoriccraft.common.item.DNAInPetriDishItem;
import net.seentro.prehistoriccraft.common.item.ExcavatorPickaxeItem;
import net.seentro.prehistoriccraft.common.item.FilledBottleItem;
import net.seentro.prehistoriccraft.common.item.FossilItem;
import net.seentro.prehistoriccraft.common.item.TissueItem;


public class PrehistoricItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(PrehistoricCraft.MODID);

    /* NATURE */

    //DAWN REDWOOD
    public static final DeferredItem<Item> DAWN_REDWOOD_SIGN = ITEMS.register("dawn_redwood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), PrehistoricBlocks.DAWN_REDWOOD_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN.get()));
    public static final DeferredItem<Item> DAWN_REDWOOD_HANGING_SIGN = ITEMS.register("dawn_redwood_hanging_sign", () -> new HangingSignItem(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN.get(), PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> DAWN_REDWOOD_BOAT = ITEMS.register("dawn_redwood_boat", () -> new BoatItem(false, PrehistoricEnumExtensions.DAWN_REDWOOD.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DAWN_REDWOOD_CHEST_BOAT = ITEMS.register("dawn_redwood_chest_boat", () -> new BoatItem(true, PrehistoricEnumExtensions.DAWN_REDWOOD.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DAWN_REDWOOD_CONE = ITEMS.register("dawn_redwood_cone", () -> new Item(new Item.Properties()));

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
    public static final DeferredItem<Item> GYPSUM_POWDER = ITEMS.registerSimpleItem("gypsum_powder");
    public static final DeferredItem<Item> PLASTER_POWDER = ITEMS.registerSimpleItem("plaster_powder");
    public static final DeferredItem<Item> PLASTER_WRAP = ITEMS.registerSimpleItem("plaster_wrap");

    /* ORES */
    public static final DeferredItem<Item> AMBER = ITEMS.registerSimpleItem("amber");
    public static final DeferredItem<Item> SULFUR = ITEMS.register("sulfur", () -> new FilledBottleItem(new Item.Properties(), PrehistoricFluids.SOURCE_ACID_FLUID.get()));

    /* BLOCK ENTITIES */
    public static final DeferredItem<Item> ACID_CLEANING_CHAMBER = ITEMS.register("acid_cleaning_chamber", () -> new AcidCleaningChamberBlockItem(new Item.Properties()));
    public static final DeferredItem<Item> TISSUE_EXTRACTION_CHAMBER = ITEMS.register("tissue_extraction_chamber", () -> new TissueExtractionChamberBlockItem(new Item.Properties()));

    /* CRAFTING MATERIALS */
    public static final DeferredItem<Item> OBSIDIAN_PLATE = ITEMS.registerSimpleItem("obsidian_plate");
    public static final DeferredItem<Item> REINFORCED_OBSIDIAN_PLATE = ITEMS.registerSimpleItem("reinforced_obsidian_plate");
    public static final DeferredItem<Item> GOLD_PIPE = ITEMS.registerSimpleItem("gold_pipe");
    public static final DeferredItem<Item> NANOPOD = ITEMS.register("nanopod", () -> new Item(new Item.Properties().durability(32).setNoRepair()));

    /* FLUIDS */
    public static final DeferredItem<Item> BLOB_OF_BLICE = ITEMS.registerSimpleItem("blob_of_blice");
    public static final DeferredItem<Item> BOTTLE_OF_BLICE = ITEMS.register("bottle_of_blice", () -> new FilledBottleItem(new Item.Properties(), PrehistoricFluids.SOURCE_BLICE_FLUID.get()));
    public static final DeferredItem<Item> BLICE_FLUID_BUCKET = ITEMS.registerItem("blice_fluid_bucket", properties -> new BucketItem(PrehistoricFluids.SOURCE_BLICE_FLUID.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> ACID_FLUID_BUCKET = ITEMS.registerItem("acid_fluid_bucket", properties -> new BucketItem(PrehistoricFluids.SOURCE_ACID_FLUID.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));

    /* GLASS INSTRUMENTS */
    public static final DeferredItem<Item> VIAL = ITEMS.registerSimpleItem("vial");
    public static final DeferredItem<Item> SYRINGE = ITEMS.registerSimpleItem("syringe");
    public static final DeferredItem<Item> JAR = ITEMS.registerSimpleItem("jar");
    public static final DeferredItem<Item> PETRI_DISH = ITEMS.register("petri_dish", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DNA_IN_A_PETRI_DISH = ITEMS.register("dna_in_a_petri_dish", () -> new DNAInPetriDishItem(new Item.Properties()));

    /* DIRECT REVIVAL ITEMS */

    public static final DeferredItem<Item> SLIME_ZYGOTE = ITEMS.register("slime_zygote", () -> new Item(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

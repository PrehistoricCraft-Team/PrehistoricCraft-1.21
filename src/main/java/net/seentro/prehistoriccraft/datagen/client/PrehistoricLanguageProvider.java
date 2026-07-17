package net.seentro.prehistoriccraft.datagen.client;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.common.entity.dinosaur.PrehistoricDinosaurEntityTypes;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

public class PrehistoricLanguageProvider extends LanguageProvider {
    private final String locale;
    public PrehistoricLanguageProvider(PackOutput output, String locale) {
        super(output, PrehistoricCraft.MODID, locale);
        this.locale = locale;
    }

    /* NOTE: This class is a mess, but if it works, it works (there isn't really a better way to do this) */

    @Override
    protected void addTranslations() {
        // English locale
        if (locale.equals("en_us")) {
            addEnglishTranslations();
        } else if (locale.equals("pl_pl")) {
            addPolishTranslations();
        }
    }

    private void addEnglishTranslations() {
        /* NATURE */

        // Dawn redwood
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_LOG, "Dawn Redwood Log");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_WOOD, "Dawn Redwood Wood");
        addBlock(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG, "Stripped Dawn Redwood Log");
        addBlock(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD, "Stripped Dawn Redwood Wood");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_LEAVES, "Dawn Redwood Leaves");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_CONES, "Dawn Redwood Cones");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_PLANKS, "Dawn Redwood Planks");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_STAIRS, "Dawn Redwood Stairs");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_SLAB, "Dawn Redwood Slab");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_FENCE, "Dawn Redwood Fence");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_FENCE_GATE, "Dawn Redwood Fence Gate");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_DOOR, "Dawn Redwood Door");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_TRAPDOOR, "Dawn Redwood Trapdoor");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_PRESSURE_PLATE, "Dawn Redwood Pressure Plate");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_BUTTON, "Dawn Redwood Button");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_SIGN, "Dawn Redwood Sign");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN, "Dawn Redwood Hanging Sign");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_SAPLING, "Dawn Redwood Sapling");

        addItem(PrehistoricItems.DAWN_REDWOOD_BOAT, "Dawn Redwood Boat");
        addItem(PrehistoricItems.DAWN_REDWOOD_CHEST_BOAT, "Dawn Redwood Chest Boat");
        addItem(PrehistoricItems.DAWN_REDWOOD_CONE, "Dawn Redwood Cone");

        addBlock(PrehistoricBlocks.NEOCALAMITES, "Neocalamites");
        addBlock(PrehistoricBlocks.NEOCALAMITES_SAPLING, "Neocalamites Sapling");
        addBlock(PrehistoricBlocks.WOOD_HORSETAIL, "Wood Horsetail");

        /* FOSSILIFEROUS STONE */
        addBlock(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE, "Cambrian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE, "Carboniferous Fossiliferous Stone");
        addBlock(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE, "Cretaceous Fossiliferous Stone");
        addBlock(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE, "Devonian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE, "Jurassic Fossiliferous Stone");
        addBlock(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE, "Neogene Fossiliferous Stone");
        addBlock(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE, "Ordovician Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE, "Paleogene Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE, "Permian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE, "Precambrian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE, "Silurian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE, "Triassic Fossiliferous Stone");

        /* PLASTERED FOSSIL STONE */
        addBlock(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE, "Plastered Cambrian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE, "Plastered Carboniferous Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE, "Plastered Cretaceous Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE, "Plastered Devonian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE, "Plastered Jurassic Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE, "Plastered Neogene Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE, "Plastered Ordovician Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE, "Plastered Paleogene Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE, "Plastered Permian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE, "Plastered Precambrian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE, "Plastered Silurian Fossiliferous Stone");
        addBlock(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE, "Plastered Triassic Fossiliferous Stone");

        /* FOSSILS */
        addItem(PrehistoricItems.CAMBRIAN_FOSSIL, "Cambrian Fossil");
        addItem(PrehistoricItems.CARBONIFEROUS_FOSSIL, "Carboniferous Fossil");
        addItem(PrehistoricItems.CRETACEOUS_FOSSIL, "Cretaceous Fossil");
        addItem(PrehistoricItems.DEVONIAN_FOSSIL, "Devonian Fossil");
        addItem(PrehistoricItems.JURASSIC_FOSSIL, "Jurassic Fossil");
        addItem(PrehistoricItems.NEOGENE_FOSSIL, "Neogene Fossil");
        addItem(PrehistoricItems.ORDOVICIAN_FOSSIL, "Ordovician Fossil");
        addItem(PrehistoricItems.PALEOGENE_FOSSIL, "Paleogene Fossil");
        addItem(PrehistoricItems.PERMIAN_FOSSIL, "Permian Fossil");
        addItem(PrehistoricItems.PRECAMBRIAN_FOSSIL, "Precambrian Fossil");
        addItem(PrehistoricItems.SILURIAN_FOSSIL, "Silurian Fossil");
        addItem(PrehistoricItems.TRIASSIC_FOSSIL, "Triassic Fossil");

        addItem(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE, "Cambrian Fossil Sample");
        addItem(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE, "Carboniferous Fossil Sample");
        addItem(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE, "Cretaceous Fossil Sample");
        addItem(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE, "Devonian Fossil Sample");
        addItem(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE, "Jurassic Fossil Sample");
        addItem(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE, "Neogene Fossil Sample");
        addItem(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE, "Ordovician Fossil Sample");
        addItem(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE, "Paleogene Fossil Sample");
        addItem(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE, "Permian Fossil Sample");
        addItem(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE, "Precambrian Fossil Sample");
        addItem(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE, "Silurian Fossil Sample");
        addItem(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE, "Triassic Fossil Sample");

        /* TOOLS */
        addItem(PrehistoricItems.EXCAVATOR_PICKAXE, "Excavator Pickaxe");
        addItem(PrehistoricItems.MAGNIFYING_GLASS, "Magnifying Glass");

        /* GYPSUM */
        addBlock(PrehistoricBlocks.GYPSUM_CRYSTAL, "Gypsum Crystal");
        addBlock(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK, "Gypsum Crystal Block");
        addItem(PrehistoricItems.GYPSUM_POWDER, "Gypsum Powder");
        addItem(PrehistoricItems.PLASTER_POWDER, "Plaster Powder");
        addItem(PrehistoricItems.PLASTER_WRAP, "Plaster Wrap");

        /* MACHINES */
        addBlock(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE, "Fossil Analysis Table");
        addBlock(PrehistoricBlocks.ACID_CLEANING_CHAMBER, "Acid Cleaning Chamber");
        addBlock(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER, "Tissue Extraction Chamber");
        addBlock(PrehistoricBlocks.DNA_SEPARATION_FILTER, "DNA Separation Filter");
        addBlock(PrehistoricBlocks.DNA_RECOMBINATOR, "DNA Recombinator");

        /* BIOLOGY */
        addItem(PrehistoricItems.ANIMAL_TISSUE, "Animal Soft Tissue");
        addItem(PrehistoricItems.PLANT_TISSUE, "Plant Soft Tissue");
        addItem(PrehistoricItems.BLOOD_CELL, "Ancient Blood Cells");

        /* AMBER */
        addBlock(PrehistoricBlocks.AMBER_BLOCK, "Amber Block");
        addBlock(PrehistoricBlocks.DEEPSLATE_AMBER_ORE, "Deepslate Amber Ore");
        addItem(PrehistoricItems.AMBER, "Amber");

        /* SULFUR */
        addBlock(PrehistoricBlocks.SULFUR_ORE, "Sulfur Ore");
        addBlock(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE, "Deepslate Sulfur Ore");
        addItem(PrehistoricItems.SULFUR, "Sulfur");

        /* SOILS */
        addBlock(PrehistoricBlocks.CRACKED_DIRT, "Cracked Dirt");
        addBlock(PrehistoricBlocks.LOAM, "Loam");
        addBlock(PrehistoricBlocks.LOAM_GRASS, "Loam Grass Block");
        addBlock(PrehistoricBlocks.LOAMY_SAND, "Loamy Sand");
        addBlock(PrehistoricBlocks.SANDY_LOAM, "Sandy Loam");
        addBlock(PrehistoricBlocks.LOAMY_SILT, "Loamy Silt");
        addBlock(PrehistoricBlocks.SILT, "Silt");
        addBlock(PrehistoricBlocks.PEAT, "Peat");
        addBlock(PrehistoricBlocks.RAW_CLAY, "Raw Clay");
        addItem(PrehistoricItems.RAW_CLAY_BALL, "Raw Clay Ball");

        /* MATERIALS */
        addItem(PrehistoricItems.OBSIDIAN_PLATE, "Obsidian Plate");
        addItem(PrehistoricItems.REINFORCED_OBSIDIAN_PLATE, "Reinforced Obsidian Plate");
        addItem(PrehistoricItems.GOLD_PIPE, "Gold Pipe");
        addItem(PrehistoricItems.NANOPOD, "Nanopod");
        addItem(PrehistoricItems.BLOB_OF_BLICE, "Blob of Blice");
        addItem(PrehistoricItems.BOTTLE_OF_BLICE, "Bottle of Blice");
        addItem(PrehistoricItems.BLICE_FLUID_BUCKET, "Bucket of Blice");
        addItem(PrehistoricItems.JAR, "Glass Jar");
        addItem(PrehistoricItems.VIAL, "Glass Vial");
        addItem(PrehistoricItems.SYRINGE, "Glass Syringe");
        addItem(PrehistoricItems.PETRI_DISH, "Petri Dish");
        addItem(PrehistoricItems.DNA_IN_A_PETRI_DISH, "DNA Sample in a Petri Dish");
        addItem(PrehistoricItems.ACID_FLUID_BUCKET, "Bucket of Acid");
        addItem(PrehistoricItems.SLIME_ZYGOTE, "Slime Zygote");

        /* FISH */
        addItem(PrehistoricItems.DAYONGASPIS_BUCKET, "Bucket of Dayongaspis");
        addItem(PrehistoricItems.RAW_DAYONGASPIS, "Raw Dayongaspis");
        addItem(PrehistoricItems.COOKED_DAYONGASPIS, "Cooked Dayongaspis");

        /* CREATIVE TABS */
        add("tabs.prehistoriccraft.prehistoriccraft_fossils", "PC Fossils");
        add("tabs.prehistoriccraft.prehistoriccraft_natural_blocks", "PC Natural Blocks");
        add("tabs.prehistoriccraft.prehistoriccraft_items", "PC Items");
        add("tabs.prehistoriccraft.prehistoriccraft_blocks", "PC Blocks");
        add("tabs.prehistoriccraft.prehistoriccraft_functional_blocks", "PC Functional Blocks");
        add("tabs.prehistoriccraft.prehistoriccraft_food", "PC Food");

        /* FLUIDS */
        add("fluid_type.prehistoriccraft.blice_fluid_type", "Blice");
        add("fluid_type.prehistoriccraft.acid_fluid_type", "Sulfuric Acid");

        /* GUI */
        add("gui.prehistoriccraft.acid_cleaning_chamber.guide", "Acid Cleaning");
        add("gui.prehistoriccraft.tissue_extraction_chamber.guide", "Tissue Extracting");
        add("gui.prehistoriccraft.dna_separation_filter.guide", "DNA Separating");
        add("gui.prehistoriccraft.dna_decontamination.guide", "DNA Decontamination");
        add("gui.prehistoriccraft.dna_recombinator.guide", "DNA Recombinator");
        add("gui.prehistoriccraft.progress_percent", "%s%% / 100%%");
        add("gui.prehistoriccraft.open_guide", "Click to open guide!");

        /* ENTITY */
        addEntityType(PrehistoricDinosaurEntityTypes.DAYONGASPIS, "Dayongaspis");
    }

    // TODO: Add all Polish translations
    private void addPolishTranslations() {
        addPolishNatureTranslations();
        addPolishMiscTranslations();
    }

    private void addPolishNatureTranslations() {
        /* DAWN REDWOOD */
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_LOG, "Pień Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_WOOD, "Drewno z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG, "Ociosany Pień Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD, "Ociosane Drewno z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_LEAVES, "Liście Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_CONES, "Szyszki Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_PLANKS, "Deski z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_STAIRS, "Schody z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_SLAB, "Płyta z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_FENCE, "Płot z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_FENCE_GATE, "Furtka z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_DOOR, "Drzwi z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_TRAPDOOR, "Klapa z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_PRESSURE_PLATE, "Płytka Naciskowa z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_BUTTON, "Guzik z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_SIGN, "Tabliczka z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN, "Podwieszana Tabliczka z Metasekwoi Chińskiej");
        addBlock(PrehistoricBlocks.DAWN_REDWOOD_SAPLING, "Sadzonka Metasekwoi Chińskiej");

        addItem(PrehistoricItems.DAWN_REDWOOD_BOAT, "Łódka z Metasekwoi Chińskiej");
        addItem(PrehistoricItems.DAWN_REDWOOD_CHEST_BOAT, "Łódka z Metasekwoi Chińskiej ze skrzynką");
        addItem(PrehistoricItems.DAWN_REDWOOD_CONE, "Szyszka Metasekwoi Chińskiej");

        /* PLANTS */
        addBlock(PrehistoricBlocks.NEOCALAMITES, "Neocalamites");
        addBlock(PrehistoricBlocks.NEOCALAMITES_SAPLING, "Neocalamites Sapling");

        /* SOILS */
        addBlock(PrehistoricBlocks.CRACKED_DIRT, "Popękana Ziemia");
        addBlock(PrehistoricBlocks.LOAM, "Ił");
        addBlock(PrehistoricBlocks.LOAM_GRASS, "Loam Grass Block");
        addBlock(PrehistoricBlocks.LOAMY_SAND, "Iłowaty Piasek");
        addBlock(PrehistoricBlocks.SANDY_LOAM, "Piaszczysty Ił");
        addBlock(PrehistoricBlocks.LOAMY_SILT, "Iłowaty Muł");
        addBlock(PrehistoricBlocks.SILT, "Muł");
        addBlock(PrehistoricBlocks.PEAT, "Torf");
        addBlock(PrehistoricBlocks.RAW_CLAY, "Surowa Glina");
        addItem(PrehistoricItems.RAW_CLAY_BALL, "Kawałek Surowej Gliny");
    }

    private void addPolishMiscTranslations() {
        /* FOSSILIFEROUS STONE */
        addBlock(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE, "Skała Kambryjska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE, "Skała Karbońska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE, "Skała Kredowa zawierająca skamieliny");
        addBlock(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE, "Skała Dewońska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE, "Skała Jurajska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE, "Skała Neogeniczna zawierająca skamieliny");
        addBlock(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE, "Skała Ordowicka zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE, "Skała Paleogeniczna zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE, "Skała Permska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE, "Skała Prekambryjska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE, "Skała Sylurska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE, "Skała Triasowa zawierająca skamieliny");

        addBlock(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE, "Otynkowana Skała Kambryjska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE, "Otynkowana Skała Karbońska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE, "Otynkowana Skała Kredowa zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE, "Otynkowana Skała Dewońska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE, "Otynkowana Skała Jurajska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE, "Otynkowana Skała Neogeniczna zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE, "Otynkowana Skała Ordowicka zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE, "Otynkowana Skała Paleogeniczna zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE, "Otynkowana Skała Permska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE, "Otynkowana Skała Prekambryjska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE, "Otynkowana Skała Sylurska zawierająca skamieliny");
        addBlock(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE, "Otynkowana Skała Triasowa zawierająca skamieliny");


        /* FOSSIL ITEMS */
        addItem(PrehistoricItems.CAMBRIAN_FOSSIL, "Kambryjska Skamielina");
        addItem(PrehistoricItems.CARBONIFEROUS_FOSSIL, "Karbońska Skamielina");
        addItem(PrehistoricItems.CRETACEOUS_FOSSIL, "Kredowa Skamielina");
        addItem(PrehistoricItems.DEVONIAN_FOSSIL, "Dewońska Skamielina");
        addItem(PrehistoricItems.JURASSIC_FOSSIL, "Jurajska Skamielina");
        addItem(PrehistoricItems.NEOGENE_FOSSIL, "Neogeniczna Skamielina");
        addItem(PrehistoricItems.ORDOVICIAN_FOSSIL, "Ordowicka Skamielina");
        addItem(PrehistoricItems.PALEOGENE_FOSSIL, "Paleogeniczna Skamielina");
        addItem(PrehistoricItems.PERMIAN_FOSSIL, "Permska Skamielina");
        addItem(PrehistoricItems.PRECAMBRIAN_FOSSIL, "Prekambryjska Skamielina");
        addItem(PrehistoricItems.SILURIAN_FOSSIL, "Sylurska Skamielina");
        addItem(PrehistoricItems.TRIASSIC_FOSSIL, "Triasowa Skamielina");

        addItem(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE, "Próbka Kambryjskiej Skamieliny");
        addItem(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE, "Próbka Karbońskiej Skamieliny");
        addItem(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE, "Próbka Kredowej Skamieliny");
        addItem(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE, "Próbka Dewońskiej Skamieliny");
        addItem(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE, "Próbka Jurajskiej Skamieliny");
        addItem(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE, "Próbka Neogenicznej Skamieliny");
        addItem(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE, "Próbka Ordowickiej Skamieliny");
        addItem(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE, "Próbka Paleogenicznej Skamieliny");
        addItem(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE, "Próbka Permskiej Skamieliny");
        addItem(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE, "Próbka Prekambryjskiej Skamieliny");
        addItem(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE, "Próbka Sylurskiej Skamieliny");
        addItem(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE, "Próbka Triasowej Skamieliny");

        /* TOOLS */
        addItem(PrehistoricItems.EXCAVATOR_PICKAXE, "Młotek Geologiczny");
        addItem(PrehistoricItems.MAGNIFYING_GLASS, "Lupa");


        /* MATERIALS */
        addBlock(PrehistoricBlocks.GYPSUM_CRYSTAL, "Kryształ Gipsu");
        addBlock(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK, "Odłam Kryształu Gipsu");

        addItem(PrehistoricItems.GYPSUM_POWDER, "Gips w proszku");
        addItem(PrehistoricItems.PLASTER_POWDER, "Gips Paryski w proszku");
        addItem(PrehistoricItems.PLASTER_WRAP, "Rolka Gipsu Paryskiego");


        /* MACHINES */
        addBlock(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE, "Stół do analizy skamielin");
        addBlock(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER, "Komora do ekstrakcji tkanek");
        addBlock(PrehistoricBlocks.ACID_CLEANING_CHAMBER, "Komora do oczyszczania kwasem");


        /* BIOLOGY */
        addItem(PrehistoricItems.ANIMAL_TISSUE, "Tkanka Miękka Zwierzęcia");
        addItem(PrehistoricItems.PLANT_TISSUE, "Tkanka Miękka Rośliny");
        addItem(PrehistoricItems.BLOOD_CELL, "Starożytne Skamieniałe Krwinki");


        /* ORES */
        addBlock(PrehistoricBlocks.AMBER_BLOCK, "Blok Bursztynu");
        addBlock(PrehistoricBlocks.DEEPSLATE_AMBER_ORE, "Łupkowa Ruda Bursztynu");
        addItem(PrehistoricItems.AMBER, "Bursztyn");

        addBlock(PrehistoricBlocks.SULFUR_ORE, "Ruda Siarki");
        addBlock(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE, "Łupkowa Ruda Siarki");

        /* TABS */
        add("tabs.prehistoriccraft.prehistoriccraft_fossils", "PC Skamieliny");
        add("tabs.prehistoriccraft.prehistoriccraft_natural_blocks", "PC Bloki Naturalne");
        add("tabs.prehistoriccraft.prehistoriccraft_items", "PC Rzeczy");
        add("tabs.prehistoriccraft.prehistoriccraft_blocks", "PC Bloki");
        add("tabs.prehistoriccraft.prehistoriccraft_functional_blocks", "PC Bloki z funkcją");

        /* GUI */
        add("gui.prehistoriccraft.acid_cleaning_chamber.guide", "Komora do oczyszczania kwasem");
        add("gui.prehistoriccraft.tissue_extraction_chamber.guide", "Ekstrakcja tkanek");
        add("gui.prehistoriccraft.dna_separation_filter.guide", "Separacja DNA");
        add("gui.prehistoriccraft.dna_decontamination.guide", "Dekontaminacja DNA");
        add("gui.prehistoriccraft.dna_recombinator.guide", "Rekombinator DNA");
        add("gui.prehistoriccraft.progress_percent", "%s%% / 100%%");
        add("gui.prehistoriccraft.open_guide", "Kliknij, aby otworzyć przewodnik!");

        /* OTHER ITEMS */
        addItem(PrehistoricItems.OBSIDIAN_PLATE, "Płyta z Obsydianu");
        addItem(PrehistoricItems.REINFORCED_OBSIDIAN_PLATE, "Wzmocniona Płyta z Obsydianu");
        addItem(PrehistoricItems.GOLD_PIPE, "Złota Rura");
        addItem(PrehistoricItems.BLOB_OF_BLICE, "Kleks z Lodopłomu");
        addItem(PrehistoricItems.BOTTLE_OF_BLICE, "Butelka Lodopłomu");
        addItem(PrehistoricItems.JAR, "Szklany Słoik");
        addItem(PrehistoricItems.VIAL, "Szklana Fiolka");
        addItem(PrehistoricItems.SYRINGE, "Szklana Strzykawka");
        addItem(PrehistoricItems.SULFUR, "Siarka");
        addItem(PrehistoricItems.PETRI_DISH, "Szalka Petriego");
        addItem(PrehistoricItems.DNA_IN_A_PETRI_DISH, "Próbka DNA w Szalce Petriego");
        addItem(PrehistoricItems.SLIME_ZYGOTE, "Zygota w Szlamie");
        addItem(PrehistoricItems.NANOPOD, "Nanopod");
        addItem(PrehistoricItems.BLICE_FLUID_BUCKET, "Wiaderko Lodopłomu");
        addItem(PrehistoricItems.ACID_FLUID_BUCKET, "Wiaderko Kwasu");
        addItem(PrehistoricItems.DAYONGASPIS_BUCKET, "Wiaderko z Dayongaspisem");
        addItem(PrehistoricItems.RAW_DAYONGASPIS, "Surowy Dayongaspis");
        addItem(PrehistoricItems.COOKED_DAYONGASPIS, "Upieczony Dayongaspis");

        /* ENTITIES */
        add("entity.prehistoriccraft.dayongaspis", "Dayongaspis");
    }
}

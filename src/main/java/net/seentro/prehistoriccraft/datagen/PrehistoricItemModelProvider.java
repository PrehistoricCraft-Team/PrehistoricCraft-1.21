package net.seentro.prehistoriccraft.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

public class PrehistoricItemModelProvider extends ItemModelProvider {
    public PrehistoricItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        /* FOSSILS */
        basicItem(PrehistoricItems.CAMBRIAN_FOSSIL.get());
        basicItem(PrehistoricItems.CARBONIFEROUS_FOSSIL.get());
        basicItem(PrehistoricItems.CRETACEOUS_FOSSIL.get());
        basicItem(PrehistoricItems.DEVONIAN_FOSSIL.get());
        basicItem(PrehistoricItems.JURASSIC_FOSSIL.get());
        basicItem(PrehistoricItems.NEOGENE_FOSSIL.get());
        basicItem(PrehistoricItems.ORDOVICIAN_FOSSIL.get());
        basicItem(PrehistoricItems.PALEOGENE_FOSSIL.get());
        basicItem(PrehistoricItems.PERMIAN_FOSSIL.get());
        basicItem(PrehistoricItems.PRECAMBRIAN_FOSSIL.get());
        basicItem(PrehistoricItems.SILURIAN_FOSSIL.get());
        basicItem(PrehistoricItems.TRIASSIC_FOSSIL.get());

        /* FOSSIL SAMPLES */
        /*basicItem(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE.get());
        basicItem(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE.get());
         */

        /* TOOLS */
        basicItem(PrehistoricItems.EXCAVATOR_PICKAXE.get());
        basicItem(PrehistoricItems.MAGNIFYING_GLASS.get());

        /* BLOCK ENTITIES */
        simpleBlockItem(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());
        basicItem(ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, "tissue_extraction_chamber"));

        /* GYPSUM & PLASTER */
        basicItem(PrehistoricItems.GYPSUM_POWDER.get());
        basicItem(PrehistoricItems.PLASTER_POWDER.get());
        basicItem(PrehistoricItems.PLASTER_WRAP.get());

        /* ORES */
        basicItem(PrehistoricItems.AMBER.get());
        basicItem(PrehistoricItems.SULFUR.get());

        /* TISSUES */
        basicItem(PrehistoricItems.ANIMAL_TISSUE.get());
        basicItem(PrehistoricItems.PLANT_TISSUE.get());
        basicItem(PrehistoricItems.BLOOD_CELL.get());

        /* CRAFTING MATERIALS */
        basicItem(PrehistoricItems.OBSIDIAN_PLATE.get());
        basicItem(PrehistoricItems.REINFORCED_OBSIDIAN_PLATE.get());
        basicItem(PrehistoricItems.GOLD_PIPE.get());

        /* BLICE */
        basicItem(PrehistoricItems.BLOB_OF_BLICE.get());
        basicItem(PrehistoricItems.BOTTLE_OF_BLICE.get());

        /* GLASS INSTRUMENTS */
        basicItem(PrehistoricItems.VIAL.get());
        basicItem(PrehistoricItems.SYRINGE.get());
        basicItem(PrehistoricItems.JAR.get());
    }
}

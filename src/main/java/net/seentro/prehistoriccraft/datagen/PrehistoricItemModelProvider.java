package net.seentro.prehistoriccraft.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        /* NATURE */
        basicItem(PrehistoricItems.RAW_CLAY_BALL.get());
        basicItem(PrehistoricItems.DAWN_REDWOOD_SIGN.get());
        basicItem(PrehistoricItems.DAWN_REDWOOD_HANGING_SIGN.get());
        basicItem(PrehistoricItems.DAWN_REDWOOD_BOAT.get());
        basicItem(PrehistoricItems.DAWN_REDWOOD_CHEST_BOAT.get());
        basicItem(PrehistoricItems.DAWN_REDWOOD_CONE.get());


        /* FOSSILS */
        basicItem(PrehistoricItems.PRECAMBRIAN_FOSSIL.get());
        basicItem(PrehistoricItems.CAMBRIAN_FOSSIL.get());
        basicItem(PrehistoricItems.ORDOVICIAN_FOSSIL.get());
        basicItem(PrehistoricItems.SILURIAN_FOSSIL.get());
        basicItem(PrehistoricItems.DEVONIAN_FOSSIL.get());
        basicItem(PrehistoricItems.CARBONIFEROUS_FOSSIL.get());
        basicItem(PrehistoricItems.PERMIAN_FOSSIL.get());
        basicItem(PrehistoricItems.TRIASSIC_FOSSIL.get());
        basicItem(PrehistoricItems.JURASSIC_FOSSIL.get());
        basicItem(PrehistoricItems.CRETACEOUS_FOSSIL.get());
        basicItem(PrehistoricItems.PALEOGENE_FOSSIL.get());
        basicItem(PrehistoricItems.NEOGENE_FOSSIL.get());

        /* FOSSIL SAMPLES */
        basicFossilSample(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE.get(), true);
        basicFossilSample(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE.get(), true);
        basicFossilSample(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE.get(), true);
        basicFossilSample(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE.get(), true);
        basicFossilSample(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE.get(), true);
        basicFossilSample(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE.get(), true);
        basicFossilSample(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE.get(), false);
        basicFossilSample(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE.get(), false);
        basicFossilSample(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE.get(), false);
        basicFossilSample(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE.get(), false);
        basicFossilSample(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE.get(), false);
        basicFossilSample(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE.get(), false);

        /* TOOLS */
        basicItem(PrehistoricItems.EXCAVATOR_PICKAXE.get());
        basicItem(PrehistoricItems.MAGNIFYING_GLASS.get());

        /* BLOCK ENTITIES */
        simpleBlockItem(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());

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
        basicItem(PrehistoricItems.NANOPOD.get());

        /* FLUIDS */
        basicItem(PrehistoricItems.BLOB_OF_BLICE.get());
        basicItem(PrehistoricItems.BOTTLE_OF_BLICE.get());
        basicItem(PrehistoricItems.BLICE_FLUID_BUCKET.get());
        basicItem(PrehistoricItems.ACID_FLUID_BUCKET.get());

        /* GLASS INSTRUMENTS */
        basicItem(PrehistoricItems.VIAL.get());
        basicItem(PrehistoricItems.SYRINGE.get());
        basicItem(PrehistoricItems.JAR.get());
        basicItem(PrehistoricItems.PETRI_DISH.get());
        basicItem(PrehistoricItems.DNA_IN_A_PETRI_DISH.get());

        /* DIRECT REVIVAL ITEMS */
        basicItem(PrehistoricItems.SLIME_ZYGOTE.get());
    }

    /* HELPER METHODS */

    public ItemModelBuilder basicItemOtherTexture(Item item, ResourceLocation texture) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture);
    }

    public ItemModelBuilder basicFossilSample(Item item, boolean deepslate) {
        return deepslate
                ? basicItemOtherTexture(item, ResourceLocation.fromNamespaceAndPath("prehistoriccraft", "item/" + "paleozoic_fossil_sample"))
                : basicItemOtherTexture(item, ResourceLocation.fromNamespaceAndPath("prehistoriccraft", "item/" + "mesozoic_fossil_sample"));
    }
}

package net.seentro.prehistoriccraft.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.seentro.prehistoriccraft.PrehistoricCraft;
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

        basicItem(PrehistoricItems.EXCAVATOR_PICKAXE.get());

        /* GYPSUM & PLASTER */
        basicItem(PrehistoricItems.GYPSUM_POWDER.get());
        basicItem(PrehistoricItems.PLASTER_POWDER.get());
        basicItem(PrehistoricItems.PLASTER_WRAP.get());
    }
}

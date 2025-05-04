package net.seentro.prehistoriccraft.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricItemModelProvider extends ItemModelProvider {
    public PrehistoricItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}

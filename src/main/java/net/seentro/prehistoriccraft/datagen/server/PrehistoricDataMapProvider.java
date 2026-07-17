package net.seentro.prehistoriccraft.datagen.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class PrehistoricDataMapProvider extends DataMapProvider {
    public PrehistoricDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        /*this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(PrehistoricBlocks.AMBER_BLOCK.getId(), new Compostable(0.25f), false);*/
    }
}

package net.seentro.prehistoriccraft.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

public class PrehistoricBlockStateProvider extends BlockStateProvider {
    public PrehistoricBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PrehistoricCraft.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(PrehistoricBlocks.AMBER_BLOCK);
    }

    /* HELPER METHODS */

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}

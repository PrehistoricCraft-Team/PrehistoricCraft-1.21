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
        /* FOSSILIFEROUS STONE */
        blockWithItem(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE);

        /* PLASTERED FOSSILIFEROUS STONE */
        blockWithItem(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE);
        blockWithItem(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE);

        blockWithItem(PrehistoricBlocks.AMBER_BLOCK);
    }

    /* HELPER METHODS */

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}

package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE);
        blockWithItemPlasterTexture(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE);

        /* ORES */
        blockWithItem(PrehistoricBlocks.AMBER_BLOCK);
        blockWithItem(PrehistoricBlocks.DEEPSLATE_AMBER_ORE);
        blockWithItem(PrehistoricBlocks.SULFUR_ORE);
        blockWithItem(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE);

        /* GYPSUM */
        itemModels().withExistingParent(PrehistoricBlocks.GYPSUM_CRYSTAL.getId().getPath(), mcLoc("item/generated"))
                        .texture("layer0", modLoc("block/" + PrehistoricBlocks.GYPSUM_CRYSTAL.getId().getPath()));
        blockWithItem(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK);

        /* NATURE */
        blockWithItem(PrehistoricBlocks.CRACKED_DIRT);
    }

    /* HELPER METHODS */
    private void blockWithItemPlasterTexture(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), models().cubeAll("plastered_fossiliferous_stone", modLoc("block/plastered_fossiliferous_stone")));
    }

    private void crossBlockWithItem(DeferredBlock<?> block) {
        directionalBlock(block.get(), models().cross(block.getId().getPath(), modLoc("block/" + block.getId().getPath())).renderType("minecraft:cutout"));
        itemModels().withExistingParent(block.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + block.getId().getPath()));
    }

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}

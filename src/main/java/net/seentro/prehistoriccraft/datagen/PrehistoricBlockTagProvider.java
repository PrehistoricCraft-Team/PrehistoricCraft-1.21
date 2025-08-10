package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PrehistoricBlockTagProvider extends BlockTagsProvider {
    public PrehistoricBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get())

                .add(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get())

                .add(PrehistoricBlocks.GYPSUM_CRYSTAL.get())
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get())

                .add(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get())

                .add(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL.get())
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get())

                .add(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());

        tag(BlockTags.DIRT)
                .add(PrehistoricBlocks.CRACKED_DIRT.get());

        tag(PrehistoricTags.Blocks.PLASTERED_FOSSIL)
                .add(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get());
    }
}

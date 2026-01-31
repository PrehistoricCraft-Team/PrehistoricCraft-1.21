package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
                .add(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get())

                .add(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get())

                .add(PrehistoricBlocks.GYPSUM_CRYSTAL.get())
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get())

                .add(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get())
                .add(PrehistoricBlocks.ACID_CLEANING_CHAMBER.get())
                .add(PrehistoricBlocks.DNA_SEPARATION_FILTER.get())
                .add(PrehistoricBlocks.DNA_RECOMBINATOR.get())

                .add(PrehistoricBlocks.AMBER_BLOCK.get())
                .add(PrehistoricBlocks.SULFUR_ORE.get())
                .add(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE.get())
                .add(PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get())

                .add(PrehistoricBlocks.TISSUE_EXTRACTION_CHAMBER.get())
                .add(PrehistoricBlocks.ACID_CLEANING_CHAMBER.get())
                .add(PrehistoricBlocks.DNA_SEPARATION_FILTER.get())
                .add(PrehistoricBlocks.DNA_RECOMBINATOR.get())

                .add(PrehistoricBlocks.AMBER_BLOCK.get())
                .add(PrehistoricBlocks.SULFUR_ORE.get())
                .add(PrehistoricBlocks.DEEPSLATE_SULFUR_ORE.get())
                .add(PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL.get())
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get())

                .add(PrehistoricBlocks.FOSSIL_ANALYSIS_TABLE.get());

        tag(BlockTags.DIRT)
                .add(PrehistoricBlocks.CRACKED_DIRT.get());
        tag(PrehistoricTags.Blocks.LOAM)
                .add(PrehistoricBlocks.LOAM_GRASS.get())
                .add(PrehistoricBlocks.LOAM.get());

        tag(PrehistoricTags.Blocks.SILT)
                .add(PrehistoricBlocks.SILT.get());
        tag(PrehistoricTags.Blocks.CLAY)
                .add(Blocks.CLAY)
                .add(PrehistoricBlocks.RAW_CLAY.get());



        tag(PrehistoricTags.Blocks.PLASTERED_FOSSIL)
                .add(PrehistoricBlocks.PLASTERED_PRECAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CAMBRIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_ORDOVICIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_SILURIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_DEVONIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CARBONIFEROUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PERMIAN_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_TRIASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_JURASSIC_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_CRETACEOUS_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_PALEOGENE_FOSSILIFEROUS_STONE.get())
                .add(PrehistoricBlocks.PLASTERED_NEOGENE_FOSSILIFEROUS_STONE.get());

        tag(BlockTags.STANDING_SIGNS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_SIGN.get());

        tag(BlockTags.WALL_SIGNS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_WALL_SIGN.get());

        tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_HANGING_SIGN.get());

        tag(BlockTags.WALL_HANGING_SIGNS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get());

        tag(BlockTags.FENCES)
                .add(PrehistoricBlocks.DAWN_REDWOOD_FENCE.get());

        tag(BlockTags.FENCES)
                .add(PrehistoricBlocks.DAWN_REDWOOD_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(PrehistoricBlocks.DAWN_REDWOOD_FENCE_GATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_BUTTON.get());

        tag(BlockTags.DOORS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_DOOR.get());

        tag(BlockTags.TRAPDOORS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_TRAPDOOR.get());

        tag(BlockTags.PRESSURE_PLATES)
                .add(PrehistoricBlocks.DAWN_REDWOOD_PRESSURE_PLATE.get());

        tag(BlockTags.SLABS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_SLAB.get());

        tag(BlockTags.STAIRS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_STAIRS.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(PrehistoricBlocks.DAWN_REDWOOD_LOG.get())
                .add(PrehistoricBlocks.DAWN_REDWOOD_WOOD.get())
                .add(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_LOG.get())
                .add(PrehistoricBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get());

        tag(BlockTags.PLANKS)
                .add(PrehistoricBlocks.DAWN_REDWOOD_PLANKS.get());

        tag(BlockTags.LEAVES)
                .add(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get());
    }
}


package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PrehistoricBlockTagProvider extends BlockTagsProvider {
    public PrehistoricBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /* MINEABLES */
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                /* FOSSILIFEROUS STONE */
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
                /* PLASTERED FOSSILIFEROUS STONE */
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
                /* GYPSUM */
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL.get())
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get());

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
                .add(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL.get())
                .add(PrehistoricBlocks.GYPSUM_CRYSTAL_BLOCK.get());

        /* OTHER */
        tag(BlockTags.DIRT)
                .add(PrehistoricBlocks.CRACKED_DIRT.get());

    }
}

package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
        /* FOSSILIFEROUS STONE */
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
                .add(PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get());

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

        /* AMBER */

        /* OTHER */
        tag(BlockTags.DIRT)
                .add(PrehistoricBlocks.CRACKED_DIRT.get());

    }
}

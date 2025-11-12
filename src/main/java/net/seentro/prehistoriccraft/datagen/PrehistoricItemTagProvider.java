package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;
import net.seentro.prehistoriccraft.registry.PrehistoricTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PrehistoricItemTagProvider extends ItemTagsProvider {
    public PrehistoricItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.PICKAXES)
                .add(PrehistoricItems.EXCAVATOR_PICKAXE.get());

        tag(PrehistoricTags.Items.FOSSILS)
                .add(PrehistoricItems.PRECAMBRIAN_FOSSIL.get())
                .add(PrehistoricItems.CAMBRIAN_FOSSIL.get())
                .add(PrehistoricItems.ORDOVICIAN_FOSSIL.get())
                .add(PrehistoricItems.SILURIAN_FOSSIL.get())
                .add(PrehistoricItems.DEVONIAN_FOSSIL.get())
                .add(PrehistoricItems.CARBONIFEROUS_FOSSIL.get())
                .add(PrehistoricItems.PERMIAN_FOSSIL.get())
                .add(PrehistoricItems.TRIASSIC_FOSSIL.get())
                .add(PrehistoricItems.JURASSIC_FOSSIL.get())
                .add(PrehistoricItems.CRETACEOUS_FOSSIL.get())
                .add(PrehistoricItems.PALEOGENE_FOSSIL.get())
                .add(PrehistoricItems.NEOGENE_FOSSIL.get());

        tag(PrehistoricTags.Items.FOSSIL_SAMPLES)
                .add(PrehistoricItems.PRECAMBRIAN_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.CAMBRIAN_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.ORDOVICIAN_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.SILURIAN_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.DEVONIAN_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.CARBONIFEROUS_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.PERMIAN_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.TRIASSIC_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.JURASSIC_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.CRETACEOUS_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.PALEOGENE_FOSSIL_SAMPLE.get())
                .add(PrehistoricItems.NEOGENE_FOSSIL_SAMPLE.get());

        tag(PrehistoricTags.Items.AMBER)
                .add(PrehistoricItems.AMBER.get());

        //Placeholder Direct Source 100% dna
        tag(PrehistoricTags.Items.DIRECT_SOURCE)
                .add(PrehistoricItems.AMBER.get());
    }
}

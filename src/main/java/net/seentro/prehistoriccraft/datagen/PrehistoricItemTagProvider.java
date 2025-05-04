package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PrehistoricItemTagProvider extends ItemTagsProvider {
    public PrehistoricItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                      CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}

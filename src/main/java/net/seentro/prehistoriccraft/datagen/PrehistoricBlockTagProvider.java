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
        /* MINING*/
        tag(BlockTags.MINEABLE_WITH_PICKAXE);
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(PrehistoricBlocks.AMBER_BLOCK.get());

        /*OTHER*/
    }
}

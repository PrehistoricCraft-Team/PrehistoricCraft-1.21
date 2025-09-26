package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricFluids;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PrehistoricFluidTagProvider extends FluidTagsProvider {
    public PrehistoricFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, PrehistoricCraft.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(FluidTags.WATER)
                .add(PrehistoricFluids.SOURCE_BLICE_FLUID.get(), PrehistoricFluids.FLOWING_BLICE_FLUID.get())
                .add(PrehistoricFluids.SOURCE_ACID_FLUID.get(), PrehistoricFluids.FLOWING_ACID_FLUID.get());
    }
}

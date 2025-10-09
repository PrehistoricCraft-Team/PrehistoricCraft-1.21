package net.seentro.prehistoriccraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.worldgen.PrehistoricBiomeModifiers;
import net.seentro.prehistoriccraft.worldgen.PrehistoricConfiguredFeatures;
import net.seentro.prehistoriccraft.worldgen.PrehistoricPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PrehistoricDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, PrehistoricConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PrehistoricPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, PrehistoricBiomeModifiers::bootstrap);

    public PrehistoricDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(PrehistoricCraft.MODID));
    }
}

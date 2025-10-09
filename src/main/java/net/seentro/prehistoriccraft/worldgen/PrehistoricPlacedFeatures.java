package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.seentro.prehistoriccraft.PrehistoricCraft;

import java.util.List;

public class PrehistoricPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD_KEY = registerKey("dawn_redwood");
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DAWN_REDWOOD_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.DAWN_REDWOOD_KEY),
                List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, DAWN_REDWOOD_BIG_KEY
                , configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.DAWN_REDWOOD_BIG_KEY),
                List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}

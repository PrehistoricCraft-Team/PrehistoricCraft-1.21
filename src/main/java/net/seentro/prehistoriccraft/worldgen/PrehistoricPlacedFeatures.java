package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;

import java.util.List;

public class PrehistoricPlacedFeatures {
    /* NATURE */

    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD_KEY = registerKey("dawn_redwood");
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    /* ORES & FOSSILS */

    public static final ResourceKey<PlacedFeature> AMBER_ORE_PLACED_KEY = registerKey("amber_ore_placed");
    public static final ResourceKey<PlacedFeature> EXTRA_AMBER_ORE_PLACED_KEY = registerKey("extra_amber_ore_placed");
    public static final ResourceKey<PlacedFeature> BURIED_AMBER_ORE_PLACED_KEY = registerKey("buried_amber_ore_placed");
    public static final ResourceKey<PlacedFeature> SULFUR_ORE_PLACED_KEY = registerKey("sulfur_ore_placed");

    public static final ResourceKey<PlacedFeature> NEOGENE_FOSSIL_PLACED_KEY = registerKey("neogene_fossil_placed");
    public static final ResourceKey<PlacedFeature> PALEOGENE_FOSSIL_PLACED_KEY = registerKey("paleogene_fossil_placed");
    public static final ResourceKey<PlacedFeature> CRETACEOUS_FOSSIL_PLACED_KEY = registerKey("cretaceous_fossil_placed");
    public static final ResourceKey<PlacedFeature> JURASSIC_FOSSIL_PLACED_KEY = registerKey("jurassic_fossil_placed");
    public static final ResourceKey<PlacedFeature> TRIASSIC_FOSSIL_PLACED_KEY = registerKey("triassic_fossil_placed");
    public static final ResourceKey<PlacedFeature> PERMIAN_FOSSIL_PLACED_KEY = registerKey("permian_fossil_placed");
    public static final ResourceKey<PlacedFeature> CARBONIFEROUS_FOSSIL_PLACED_KEY = registerKey("carboniferous_fossil_placed");
    public static final ResourceKey<PlacedFeature> DEVONIAN_FOSSIL_PLACED_KEY = registerKey("devonian_fossil_placed");
    public static final ResourceKey<PlacedFeature> SILURIAN_FOSSIL_PLACED_KEY = registerKey("silurian_fossil_placed");
    public static final ResourceKey<PlacedFeature> ORDOVICIAN_FOSSIL_PLACED_KEY = registerKey("ordovician_fossil_placed");
    public static final ResourceKey<PlacedFeature> CAMBRIAN_FOSSIL_PLACED_KEY = registerKey("cambrian_fossil_placed");
    public static final ResourceKey<PlacedFeature> PRECAMBRIAN_FOSSIL_PLACED_KEY = registerKey("precambrian_fossil_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        /* NATURE */

        register(context, DAWN_REDWOOD_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.DAWN_REDWOOD_KEY),
                List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, DAWN_REDWOOD_BIG_KEY
                , configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.DAWN_REDWOOD_BIG_KEY),
                List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        /* ORES & FOSSILS */

        register(context, AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.AMBER_ORE_KEY),
                OrePlacementUtils.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

        register(context, EXTRA_AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.EXTRA_AMBER_ORE_KEY),
                OrePlacementUtils.commonOrePlacement(11, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

        register(context, BURIED_AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.BURIED_AMBER_ORE_KEY),
                OrePlacementUtils.commonOrePlacement(9, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

        register(context, SULFUR_ORE_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.SULFUR_ORE_KEY),
                OrePlacementUtils.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(25))));

        registerFossil(context, NEOGENE_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.NEOGENE_FOSSIL_KEY), 55, 64);
        registerFossil(context, PALEOGENE_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.PALEOGENE_FOSSIL_KEY), 45, 54);
        registerFossil(context, CRETACEOUS_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.CRETACEOUS_FOSSIL_KEY), 35, 44);
        registerFossil(context, JURASSIC_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.JURASSIC_FOSSIL_KEY), 25, 34);
        registerFossil(context, TRIASSIC_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.TRIASSIC_FOSSIL_KEY), 15, 24);
        registerFossil(context, PERMIAN_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.PERMIAN_FOSSIL_KEY), 5, 14);
        registerFossil(context, CARBONIFEROUS_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.CARBONIFEROUS_FOSSIL_KEY), -5, 4);
        registerFossil(context, DEVONIAN_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.DEVONIAN_FOSSIL_KEY), -15, -6);
        registerFossil(context, SILURIAN_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.SILURIAN_FOSSIL_KEY), -25, -16);
        registerFossil(context, ORDOVICIAN_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.ORDOVICIAN_FOSSIL_KEY), -35, -26);
        registerFossil(context, CAMBRIAN_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.CAMBRIAN_FOSSIL_KEY), -50, -36);
        registerFossil(context, PRECAMBRIAN_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(PrehistoricConfiguredFeatures.PRECAMBRIAN_FOSSIL_KEY), -64, -51);
    }

    public static void registerFossil(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, int minPos, int maxPos) {
        register(context, key, configuration, OrePlacementUtils.commonOrePlacement(18, HeightRangePlacement.uniform(VerticalAnchor.absolute(minPos), VerticalAnchor.absolute(maxPos))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}

package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricBiomeModifiers {
    /* NATURE */

    public static final ResourceKey<BiomeModifier> DAWN_REDWOOD_KEY = registerKey("dawn_redwood");
    public static final ResourceKey<BiomeModifier> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    /* ORES & FOSSILS */

    public static final ResourceKey<BiomeModifier> ADD_AMBER_ORE = registerKey("add_amber_ore");
    public static final ResourceKey<BiomeModifier> ADD_EXTRA_AMBER_ORE = registerKey("add_extra_amber_ore");
    public static final ResourceKey<BiomeModifier> ADD_BURIED_AMBER_ORE = registerKey("add_buried_amber_ore");
    public static final ResourceKey<BiomeModifier> ADD_SULFUR_ORE = registerKey("add_sulfur_ore");

    public static final ResourceKey<BiomeModifier> ADD_NEOGENE_FOSSIL_KEY = registerKey("add_neogene_fossil");
    public static final ResourceKey<BiomeModifier> ADD_PALEOGENE_FOSSIL_KEY = registerKey("add_paleogene_fossil");
    public static final ResourceKey<BiomeModifier> ADD_CRETACEOUS_FOSSIL_KEY = registerKey("add_cretaceous_fossil");
    public static final ResourceKey<BiomeModifier> ADD_JURASSIC_FOSSIL_KEY = registerKey("add_jurassic_fossil");
    public static final ResourceKey<BiomeModifier> ADD_TRIASSIC_FOSSIL_KEY = registerKey("add_triassic_fossil");
    public static final ResourceKey<BiomeModifier> ADD_PERMIAN_FOSSIL_KEY = registerKey("add_permian_fossil");
    public static final ResourceKey<BiomeModifier> ADD_CARBONIFEROUS_FOSSIL_KEY = registerKey("add_carboniferous_fossil");
    public static final ResourceKey<BiomeModifier> ADD_DEVONIAN_FOSSIL_KEY = registerKey("add_devonian_fossil");
    public static final ResourceKey<BiomeModifier> ADD_SILURIAN_FOSSIL_KEY = registerKey("add_silurian_fossil");
    public static final ResourceKey<BiomeModifier> ADD_ORDOVICIAN_FOSSIL_KEY = registerKey("add_ordovician_fossil");
    public static final ResourceKey<BiomeModifier> ADD_CAMBRIAN_FOSSIL_KEY = registerKey("add_cambrian_fossil");
    public static final ResourceKey<BiomeModifier> ADD_PRECAMBRIAN_FOSSIL_KEY = registerKey("add_precambrian_fossil");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        /* NATURE */

        context.register(DAWN_REDWOOD_KEY, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                HolderSet.direct(placedFeatures.getOrThrow(PrehistoricPlacedFeatures.DAWN_REDWOOD_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(DAWN_REDWOOD_BIG_KEY, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                HolderSet.direct(placedFeatures.getOrThrow(PrehistoricPlacedFeatures.DAWN_REDWOOD_BIG_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        /* ORES & FOSSILS */

        context.register(ADD_AMBER_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PrehistoricPlacedFeatures.AMBER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_EXTRA_AMBER_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                HolderSet.direct(placedFeatures.getOrThrow(PrehistoricPlacedFeatures.EXTRA_AMBER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_BURIED_AMBER_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PrehistoricPlacedFeatures.BURIED_AMBER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_SULFUR_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PrehistoricPlacedFeatures.SULFUR_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        registerFossil(context, ADD_NEOGENE_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.NEOGENE_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_PALEOGENE_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.PALEOGENE_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_CRETACEOUS_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.CRETACEOUS_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_JURASSIC_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.JURASSIC_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_TRIASSIC_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.TRIASSIC_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_PERMIAN_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.PERMIAN_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_CARBONIFEROUS_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.CARBONIFEROUS_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_DEVONIAN_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.DEVONIAN_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_SILURIAN_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.SILURIAN_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_ORDOVICIAN_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.ORDOVICIAN_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_CAMBRIAN_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.CAMBRIAN_FOSSIL_PLACED_KEY));
        registerFossil(context, ADD_PRECAMBRIAN_FOSSIL_KEY, biomes.getOrThrow(BiomeTags.IS_OVERWORLD), placedFeatures.getOrThrow(PrehistoricPlacedFeatures.PRECAMBRIAN_FOSSIL_PLACED_KEY));
    }

    public static void registerFossil(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> key, HolderSet<Biome> biome, Holder<PlacedFeature> placement) {
        context.register(key, new BiomeModifiers.AddFeaturesBiomeModifier(
                biome,
                HolderSet.direct(placement),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }
}

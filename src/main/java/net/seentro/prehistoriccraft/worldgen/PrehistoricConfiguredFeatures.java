package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.worldgen.tree.DawnRedwoodFoliagePlacer;
import net.seentro.prehistoriccraft.worldgen.tree.DawnRedwoodTrunkPlacer;

import java.util.List;

public class PrehistoricConfiguredFeatures {

    /* NATURE */

    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_KEY = registerKey("dawn_redwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    /* ORES & FOSSILS */

    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER_ORE_KEY = registerKey("amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EXTRA_AMBER_ORE_KEY = registerKey("extra_amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BURIED_AMBER_ORE_KEY = registerKey("buried_amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_ORE_KEY = registerKey("sulfur_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> NEOGENE_FOSSIL_KEY = registerKey("neogene_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALEOGENE_FOSSIL_KEY = registerKey("paleogene_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRETACEOUS_FOSSIL_KEY = registerKey("cretaceous_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JURASSIC_FOSSIL_KEY = registerKey("jurassic_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TRIASSIC_FOSSIL_KEY = registerKey("triassic_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PERMIAN_FOSSIL_KEY = registerKey("permian_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CARBONIFEROUS_FOSSIL_KEY = registerKey("carboniferous_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEVONIAN_FOSSIL_KEY = registerKey("devonian_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILURIAN_FOSSIL_KEY = registerKey("silurian_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORDOVICIAN_FOSSIL_KEY = registerKey("ordovician_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAMBRIAN_FOSSIL_KEY = registerKey("cambrian_fossil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRECAMBRIAN_FOSSIL_KEY = registerKey("precambrian_fossil");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        /* NATURE */

        register(context, DAWN_REDWOOD_BIG_KEY, Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LOG.get()),
                    new DawnRedwoodTrunkPlacer(35, 5, 5), //Random is 35|| 35+5 || 35+5+5
                    BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()),
                    new DawnRedwoodFoliagePlacer(UniformInt.of(8, 10), UniformInt.of(12, 18), UniformInt.of(45, 50)),
                    new TwoLayersFeatureSize(2, 0, 10)).build());

        register(context, DAWN_REDWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LOG.get()),
                new StraightTrunkPlacer(24, 2, 1), //Random is 24|| 24+2 || 24+2+1
                BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()),
                new DawnRedwoodFoliagePlacer(UniformInt.of(5, 8), UniformInt.of(4, 8), UniformInt.of(24, 30)),
                new TwoLayersFeatureSize(2, 0, 10)).build());

        /* ORES & FOSSILS */

        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> sulfurOres = List.of(OreConfiguration.target(stoneReplaceables, PrehistoricBlocks.SULFUR_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables, PrehistoricBlocks.DEEPSLATE_SULFUR_ORE.get().defaultBlockState()));

        register(context, AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(deepslateReplaceables, PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState(), 2));
        register(context, EXTRA_AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(deepslateReplaceables, PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState(), 5));
        register(context, BURIED_AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(deepslateReplaceables, PrehistoricBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState(), 3, 0.9F));

        register(context, SULFUR_ORE_KEY, Feature.ORE, new OreConfiguration(sulfurOres, 6, 0.05F));

        registerFossil(context, NEOGENE_FOSSIL_KEY, stoneReplaceables, PrehistoricBlocks.NEOGENE_FOSSILIFEROUS_STONE.get());
        registerFossil(context, PALEOGENE_FOSSIL_KEY, stoneReplaceables, PrehistoricBlocks.PALEOGENE_FOSSILIFEROUS_STONE.get());
        registerFossil(context, CRETACEOUS_FOSSIL_KEY, stoneReplaceables, PrehistoricBlocks.CRETACEOUS_FOSSILIFEROUS_STONE.get());
        registerFossil(context, JURASSIC_FOSSIL_KEY, stoneReplaceables, PrehistoricBlocks.JURASSIC_FOSSILIFEROUS_STONE.get());
        registerFossil(context, TRIASSIC_FOSSIL_KEY, stoneReplaceables, PrehistoricBlocks.TRIASSIC_FOSSILIFEROUS_STONE.get());
        registerFossil(context, PERMIAN_FOSSIL_KEY, stoneReplaceables, PrehistoricBlocks.PERMIAN_FOSSILIFEROUS_STONE.get());
        registerFossil(context, CARBONIFEROUS_FOSSIL_KEY, deepslateReplaceables, PrehistoricBlocks.CARBONIFEROUS_FOSSILIFEROUS_STONE.get());
        registerFossil(context, DEVONIAN_FOSSIL_KEY, deepslateReplaceables, PrehistoricBlocks.DEVONIAN_FOSSILIFEROUS_STONE.get());
        registerFossil(context, SILURIAN_FOSSIL_KEY, deepslateReplaceables, PrehistoricBlocks.SILURIAN_FOSSILIFEROUS_STONE.get());
        registerFossil(context, ORDOVICIAN_FOSSIL_KEY, deepslateReplaceables, PrehistoricBlocks.ORDOVICIAN_FOSSILIFEROUS_STONE.get());
        registerFossil(context, CAMBRIAN_FOSSIL_KEY, deepslateReplaceables, PrehistoricBlocks.CAMBRIAN_FOSSILIFEROUS_STONE.get());
        registerFossil(context, PRECAMBRIAN_FOSSIL_KEY, deepslateReplaceables, PrehistoricBlocks.PRECAMBRIAN_FOSSILIFEROUS_STONE.get());
    }

    public static void registerFossil(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, RuleTest replaceables, Block block) {
        register(context, key, Feature.ORE, new OreConfiguration(replaceables, block.defaultBlockState(), 5));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}

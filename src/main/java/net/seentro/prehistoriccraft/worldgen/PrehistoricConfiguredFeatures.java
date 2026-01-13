package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricBlocks;
import net.seentro.prehistoriccraft.registry.PrehistoricFeatures;
import net.seentro.prehistoriccraft.worldgen.tree.DawnRedwoodFoliagePlacer;
import net.seentro.prehistoriccraft.worldgen.tree.DawnRedwoodTrunkPlacer;

public class PrehistoricConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_TREE_KEY = registerKey("dawn_redwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_KEY = registerKey("dawn_redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, DAWN_REDWOOD_KEY, PrehistoricFeatures.DAWN_REDWOOD_FEATURE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, DAWN_REDWOOD_BIG_KEY, Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LOG.get()),
                    new DawnRedwoodTrunkPlacer(35, 5, 5), //Random is 35|| 35+5 || 35+5+5
                    BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()),
                    new DawnRedwoodFoliagePlacer(UniformInt.of(8, 10), UniformInt.of(12, 18), UniformInt.of(45, 50)),
                    new TwoLayersFeatureSize(2, 0, 10)).build()
    );

        register(context, DAWN_REDWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LOG.get()),
                new StraightTrunkPlacer(24, 2, 1), //Random is 24|| 24+2 || 24+2+1
                BlockStateProvider.simple(PrehistoricBlocks.DAWN_REDWOOD_LEAVES.get()),
                new DawnRedwoodFoliagePlacer(UniformInt.of(5, 8), UniformInt.of(4, 8), UniformInt.of(24, 30)),
                new TwoLayersFeatureSize(2, 0, 10)).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}

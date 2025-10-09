package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.seentro.prehistoriccraft.PrehistoricCraft;
import net.seentro.prehistoriccraft.registry.PrehistoricFeatures;
import net.seentro.prehistoriccraft.registry.PrehistoricItems;

public class PrehistoricConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_KEY = registerKey("dawn_redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, DAWN_REDWOOD_KEY, PrehistoricFeatures.DAWN_REDWOOD_FEATURE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, DAWN_REDWOOD_BIG_KEY, PrehistoricFeatures.DAWN_REDWOOD_BIG_FEATURE.get(), NoneFeatureConfiguration.INSTANCE);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}

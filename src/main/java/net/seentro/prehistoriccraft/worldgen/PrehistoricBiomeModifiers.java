package net.seentro.prehistoriccraft.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.seentro.prehistoriccraft.PrehistoricCraft;

public class PrehistoricBiomeModifiers {
    public static final ResourceKey<BiomeModifier> DAWN_REDWOOD_KEY = registerKey("dawn_redwood");
    public static final ResourceKey<BiomeModifier> DAWN_REDWOOD_BIG_KEY = registerKey("dawn_redwood_big");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

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
    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(PrehistoricCraft.MODID, name));
    }
}
